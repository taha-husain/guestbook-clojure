$(document).ready(function(){
  $("#confirm-password").on("blur", function(event){
    comparePasswords();
  });

  $("#signupForm").on("submit", function(event){
    comparePasswords();
  });
})

function comparePasswords() {
  if($("#password").val() !== $("#confirm-password").val()) {
    alert("Passwords do not match");
    return false;
  }
  return true;
}
