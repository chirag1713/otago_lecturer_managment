/**
 * 
 */

function msgPopup(msg, type, timeout) {
    var $pop_up = $("#msg_pop_up");
    $pop_up.removeClass("alert error");
    $("p", $pop_up).html(msg);
    $("h2", $pop_up).html("Success");
    if (type && type == 1) {
        $pop_up.addClass("alert");
        $("h2", $pop_up).html("Alert");
    } else if (type && type == 2) {
        $pop_up.addClass("error");
        $("h2", $pop_up).html("Error");
    }

    $pop_up.modal();

    if (timeout) {
        setTimeout(function() {
            $(".btn", $pop_up).trigger("click");
        }, timeout);
    }
}

function initLogin() {
	alert("dffffff");
	var $loginform = $("#loginform");
	var onLoginClick = function() {
		// if (AFRO.BlockConcurrentReq(1000)) {
		// return;
		// }
		$loginform.validate({
			highlight : function(element) {
				$(element).parent().addClass("has-error");
			},
			unhighlight : function(element) {
				$(element).parent().removeClass("has-error");
			},
			errorPlacement : function(error, element) {
				// element.next("label").remove().end().after(error);
			},
			rules : {
				email : {
					required : true,
					email : true
				},
				password : {
					required : true
				}
			}
		});
		if (!$loginform.valid()) {
			return;
		}
		var param = getParamObj($loginform);
		// oLoadingDiv.show();
		jqueryAjax("auth/login", param, function(res) {
			// AFRO.ResetBlockConcurrentReq(0);
			// oLoadingDiv.hide();
			if (res.STATUS !== 1) {
			msgPopup(res.MESSAGE, 2);
				return;
			}
			//msgPopup(res.MESSAGE);
			window.location.href = baseUrl + "course";
		});
	}
	$("#btn-login").on("click", onLoginClick);
}

function popUpForm() {
	$("#multi-tab #tab2").hide();
	alert("fdfs");

	$("#multi-tab #tab1").show();

	$("#items").click(function() {
		alert("fsdfs");
		$("#tab2").hide();
		$("#tab1").show();
	});

	$("#capabilities").click(function() {
		$("#tab1").hide();
		$("#tab2").show();
	});
	
	var clearForm = function(){
		$("#popup-form")[0].reset();
	};
	clearForm();

	$("#popup-form #cl").on("click", function() {
		$("#myModal").modal("hide");
		clearForm();
	});

	$("#clx").click(function() {
		$("#myModal").modal("hide");
		clearForm();
	});

	var $popupform = $("#popup-form");
	var onPopUpClick = function() {

		$popupform.validate({
			highlight : function(element) {
				$(element).parent().addClass("has-error");
			},
			unhighlight : function(element) {
				$(element).parent().removeClass("has-error");
			},
			errorPlacement : function(error, element) {
				element.next("label").remove().end().after(error);
			},
			rules : {
				duration : {
					required : true,
				},
				cantent : {
					required : true
				}
			}
		});

		if (!$loginform.valid()) {
			return;
		}

		var param = getParamObj($loginform);
		var data = {
			"data" : JSON.stringify(param)
		};
		oLoadingDiv.show();
		jqueryAjax("auth/login", data, function(res) {
			AFRO.ResetBlockConcurrentReq(0);
			oLoadingDiv.hide();
			if (res.STATUS == 1) {
				alert(res.MESSAGE);
				$("#myModal").modal("hide");
				clearForm();
			}
		});
	}

	$("#pop-btn").on("click", onPopUpClick);

}

function test(id) {
	alert(id);
	$("#popup-form #hidden-id").val(id);
	$("#myModal").modal();
	// document.getElementById('myModal').style.display = "block";

}