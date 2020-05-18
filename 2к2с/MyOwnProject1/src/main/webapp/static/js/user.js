function deleteSetups(token) {
	$.ajax({
		url: "/api/setups",
		headers: {
			"Authorization" : token
		},
		method: "DELETE",
		contentType: "application/json",
		dataType: "json",
		complete: function (response) {
			location.reload();
		}
	});
}