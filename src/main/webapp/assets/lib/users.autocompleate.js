$(document).ready(function () {
    var options, a;
    jQuery(function(){
        options = { serviceUrl:'/admin/criteria' };
        a = $('#query').autocomplete(options);
    });
});