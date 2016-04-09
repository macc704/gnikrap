/*
 * Gnikrap is a simple scripting environment for the Lego Mindstrom EV3
 * Copyright (C) 2014-2015 Jean BENECH
 *
 * Gnikrap is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Gnikrap is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Gnikrap.  If not, see <http://www.gnu.org/licenses/>.
 */

 
// Model that manage the "load/manage file" dialog
function ManageFilesViewModel(appContext) {
  'use strict';

  var self = this;
  { // Init
    self.context = appContext; // The application context
    self.files = ko.observableArray();
    self.loadFile = undefined;
    self.getFileListURL = undefined;
    self.getDeleteFileURL = undefined;
  }

  // Callbacks in order to manage the files:
  //  - loadFile(filename): void - Function that load the file, no return expected
  //  - getFileListURL(): String - Function that return the URL to use to retrive the list of files (JSON list of string)
  //  - getDeleteFileURL(filename): String - Function that return the URL to call to delete the file
  self.display = function(loadFile, getFileListURL, getDeleteFileURL) {
    self.loadFile = loadFile;
    self.getFileListURL = getFileListURL;
    self.getDeleteFileURL = getDeleteFileURL;
    self.doRefreshFileList();
    $('#manageFilesModal').modal('show');
  };

  self.hide = function() {
    self.loadFile = undefined;
    self.getFileListURL = undefined;
    self.getDeleteFileURL = undefined;
    $('#manageFilesModal').modal('hide');
  };

  self.doRefreshFileList = function() {
    // Retrieve the list from the server
    self.files.removeAll();
    if(self.getFileListURL) {
      $.ajax({
        url: self.getFileListURL(),
        success: function(data, status) {
          var scriptFiles = JSON.parse(data);
          for(var i = 0; i < scriptFiles.length; i++) {
            //console.log("Adding: " + scriptFiles[i]);
            scriptFiles[i].isReadWrite = (scriptFiles[i].name.indexOf("__") != 0);
            self.files.push(scriptFiles[i]);
          }
          $("#manageFilesModal .i18n").i18n(); // DOM generated by Knockout isn't i18n => Need to re-translate the modal
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
          // XMLHttpRequest.status: HTTP response code
          self.context.messageLogVM.addMessage(true, i18n.t("manageFilesModal.errors.cantRetrieveListOfFiles",
            { causedBy: ("" + XMLHttpRequest.status + " - " +  errorThrown)}));
          self.hide();
        }
      });
    } else {
      console.log("Manage File Dialog not properly used, self.getFileListURL is not set");
    }
  };

  self.onLoad = function(file) {
    var loadFile = self.loadFile;
    self.hide();
    if(loadFile) {
      loadFile(file.name);
    } else {
      console.log("Manage File Dialog not properly used, self.loadFile is not set");
    }
  };

  self.onDelete = function(file) {
    if(self.getDeleteFileURL) {
      bootbox.confirm(i18n.t("manageFilesModal.confirmFileDeletion", { filename: file.name }), function(result) {
        if(result) {
          self.files.remove(file);
          $.ajax({
            url: self.getDeleteFileURL(file.name),
            type: "DELETE",
            success: function(data, status) {
              console.log("Script file: '" + file.name + "' successfully deleted");
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
              // XMLHttpRequest.status: HTTP response code
              alert(i18n.t("manageFilesModal.errors.cantDeleteFile",
                  { filename: result, causedBy: ("" + XMLHttpRequest.status + " - " +  errorThrown)}));
              self.doRefreshList();
            }
          });
        } // else cancel
      });
    } else {
      console.log("Manage File Dialog not properly used, self.getDeleteFileURL is not set");
    }
  };
}
