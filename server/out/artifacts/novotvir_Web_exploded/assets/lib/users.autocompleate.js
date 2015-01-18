$(document).ready(function () {
    var a;
    jQuery(function(){
        a = $('#query').autocomplete({
            serviceUrl:'/admin/criteria',
            delimiter: /\s*/,
            maxHeight:400,
            width:300,
            zIndex: 9999,
            deferRequestBy: 0 //miliseconds
        });
    });
});
