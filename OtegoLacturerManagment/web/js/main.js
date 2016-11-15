
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//function jqueryAjax(url, param, callback, errorFn) {
//    jQuery.ajax({
//        url: baseUrl + "/" + url,
//        data: param,
//        type: "POST",
//        success: callback,
//        dataType: "json",
//        error: function(xhr) {
//            oLoadingDiv.hide();
//            if (errorFn) {
//                if (errorFn == true) {
//                    msgPopup("something went wrong.", 2);
//                } else {
//                    errorFn(xhr);
//                }
//            }
//            //alert(xhr.status);
//        }
//    });
//}

function jqueryAjax(url, param, callback) {    
	jQuery.ajax({
        url: baseUrl + "/" + url,
        data: param,
        type: "POST",
        success: callback,
        dataType: "json",      
        error: function(xhr) {
            alert(xhr.status);
        }
    });
}

function getParamObj($container) {
    var param = new Object();
    $("input[type=text],input[type=email],input[type=tel],input[type=hidden],input[type=password],select,input[type=checkbox]:checked,input[type=radio]:checked,textarea", $container).each(function() {
        var $this = $(this);
        if ($this.attr('name') && $this.attr('name').length > 0) {
            if ($this.attr('datatype') && $this.attr('datatype') == "int") {
                param[$this.attr('name')] = $this.val().length > 0 ? parseInt($.trim($this.val())) : 0;
            } else {
                param[$this.attr('name')] = $.trim($this.val());
            }
        }
    });
    return param;
}

function initAddUpdate() {
	var $add_form = $("#reg-form");
    $("#create-account", $add_form).click(function() {
        var $container = $($add_form);
        alert($container);
        var param = getParamObj($container)
        alert(param);
        var data = new Object();
        data["data"] = JSON.stringify(param);
        alert(data)
        jqueryAjax("auth/signup",data,function(res) {
            if (res.STATUS != 1) {
          alert(res.MESSAGE);
            }else{
          //  window.location.href "/auth/signup";
            alert("Added Successfully!");
            }
        });
    });
}
   