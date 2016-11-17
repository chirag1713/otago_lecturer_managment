/**
 * 
 */
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
				alert(res.MESSAGE);
				return;
			}
			window.location.href = baseUrl + "course";
		});
	};
	$("#btn-login").on("click", onLoginClick);
}