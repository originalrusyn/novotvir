/*<![CDATA[*/
$(document).ready(function() {
    $('#signUpForm').submit(function(e) {
        var pass = $('#passInput').val();
        var confirmPass = $('#confirmPassInput').val();
        if (pass != confirmPass) {
            var error = [[#{signup.pass.is.not.as.passConfirm}]];
            $('#confirmPassParagraph').text(error);
            e.preventDefault();
        }else {
            var token = $().crypt({
                method: "md5",
                source: pass
            });
            $('#tokenInput').val(token);
        }
    });
});
/*]]>*/