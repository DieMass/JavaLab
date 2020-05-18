function getCpus(socketName) {
	$.ajax({
		url: "/api/cpus",
		data: {'socketName': socketName},
		method: "GET",
		contentType: "application/json",
		dataType: "json",
		success: function (response) {
			var cpu = document.getElementById("cpu");
			cpu.innerHTML = "";
			$('#cpu').first().append('<option value="">' + 'Все CPU' + '</option>');
			$.each(response.data, function () {
				$('#cpu').first().append('<option id="' + this.id + '" value="' + this.socket + '">' + this.family + ' ' + this.line + ' ' + this.series + '</option>');
			});
			$('#cpu').first().append('<option hidden value="' + socketName + '">' + 'currentSocket' + '</option>');
		}
	});
}

function getMoBos(socketName, pciName) {

	$.ajax({
		url: "/api/mobos",
		data: {'socketName': socketName, 'pciName': pciName},
		method: "GET",
		contentType: "application/json",
		dataType: "json",
		success: function (response) {
			var mobo = $('#mobo');
			mobo.empty();
			mobo.append('<option value=";">Все материнские платы</option>');
			$.each(response.data, function () {
				mobo.append('<option id="' + this.id + '" value="' + this.socket + ';' + this.pci + '">' + this.name + '</option>');
			});
			mobo.append('<option hidden value="' + socketName + '">' + 'currentSocket' + '</option>');
			mobo.append('<option hidden value="' + pciName + '">' + 'currentPci' + '</option>');
		}
	});
}

function getGpus(pciName) {
	$.ajax({
		url: "/api/gpus?pci=" + pciName,
		method: "GET",
		contentType: "application/json",
		dataType: "json",
		success: function (response) {
			var gpu = $('#gpu');
			gpu.empty();
			gpu.append('<option value="">Все GPU</option>');
			$.each(response.data, function () {
				gpu.append('<option id="' + this.id + '" value="' + this.pci + '">' + this.name + '</option>');
			});
			gpu.append('<option hidden value="' + pciName + '">' + 'currentPci' + '</option>');
		}
	});
}

function getAdapter(socketName, pciName, field) {
	var currentSocket = $('#mobo option:contains("currentSocket")').val();
	var currentPci = $('#mobo option:contains("currentPci")').val();
	//in first time or when somewhere choose all
	if (socketName === "" && pciName === "") {
		$('#mobo').val($('#mobo option:first').val());
		$('#cpu').val($('#cpu option:first').val());
		$('#gpu').val($('#gpu option:first').val());
		getCpus(socketName);
		getMoBos(socketName, pciName);
		getGpus(pciName);
		setCpu();
		setMoBo();
		setGpu();
		//when choose cpu
	} else if (pciName === "") {
		if (currentSocket !== socketName) {
			getMoBos(socketName, currentPci);
			setCurrentSocket(socketName, 'cpu');
		}
		//when choose gpu
	} else if (socketName === "") {
		l(socketName, pciName, 'gpu');
		if (currentPci !== pciName) {
			l(currentSocket, currentPci, 'gpu');
			getMoBos(currentSocket, pciName);
			setCurrentPci(pciName, 'gpu');
		}
		//when choose mobo
	} else {
		currentSocket = $('#cpu option:contains("currentSocket")').val();
		currentPci = $('#gpu option:contains("currentPci")').val();
		if (currentSocket !== socketName) {
			getCpus(socketName);
			setCurrentSocket(socketName, 'mobo');
		}
		if (currentPci !== pciName) {
			getGpus(pciName);
			setCurrentPci(pciName, 'mobo');

		}
	}
}

function l(socket, pci, field) {
	console.log("socket in " + field + " is " + socket);
	console.log("pci in " + field + " is " + pci);

}

function setCurrentSocket(socketName, field) {
	$('#cpu option:contains("currentSocket")').val(socketName);
	$('#mobo option:contains("currentSocket")').val(socketName);
}

function setCurrentPci(pciName, field) {
	$('#gpu option:contains("currentPci")').val(pciName);
	$('#mobo option:contains("currentPci")').val(pciName);
}

function setCpu() {
	var cpuId = $('#cpu option:selected').attr('id');
	var cpuTable = $('#cpuTable');
	var socketTable = $('#socketTable');
	cpuTable.empty();
	socketTable.empty();
	if (cpuId !== undefined) {
		$.ajax({
			url: "/api/cpus/byId?id=" + cpuId,
			method: "GET",
			contentType: "application/json",
			dataType: "json",
			success: function (response) {
				socketTable.append('<tr><td>Socket</td><td>' + response.data.socket + '</td></tr>');
				cpuTable.append('<tr><td>Family</td><td>' + response.data.family + '</td></tr>');
				cpuTable.append('<tr><td>Line</td><td>' + response.data.line + '</td></tr>');
				cpuTable.append('<tr><td>Series</td><td>' + response.data.series + '</td></tr>');
				cpuTable.append('<tr><td>Company</td><td>' + response.data.company + '</td></tr>');
				cpuTable.append('<tr><td>Cores</td><td>' + response.data.cores + '</td></tr>');
				cpuTable.append('<tr><td>Threads</td><td>' + response.data.threads + '</td></tr>');
				cpuTable.append('<tr><td>Base clock</td><td>' + response.data.baseClock + '</td></tr>');
				cpuTable.append('<tr><td>Max clock for single core</td><td>' + response.data.maxClockSingleCore + '</td></tr>');
				cpuTable.append('<tr><td>Lithography</td><td>' + response.data.lithography + '</td></tr>');
				cpuTable.append('<tr><td>TDP</td><td>' + response.data.tdp + '</td></tr>');
			}
		});
	}
}

function setMoBo() {
	var moBoId = $('#mobo option:selected').attr('id');
	var moBoTable = $('#moBoTable');
	var socketTable = $('#socketTable');
	var pciTable = $('#pciTable');
	moBoTable.empty();
	socketTable.empty();
	pciTable.empty();
	if (moBoId !== undefined) {
		$.ajax({
			url: "/api/mobos/byId?id=" + moBoId,
			method: "GET",
			contentType: "application/json",
			dataType: "json",
			success: function (response) {
				socketTable.append('<tr><td>Socket</td><td>' + response.data.socket + '</td></tr>');
				pciTable.append('<tr><td>PCI-e version</td><td>' + response.data.pci + '</td></tr>');
				moBoTable.append('<tr><td>Company</td><td>' + response.data.company + '</td></tr>');
				moBoTable.append('<tr><td>Name</td><td>' + response.data.name + '</td></tr>');
				moBoTable.append('<tr><td>Chipset</td><td>' + response.data.chipset + '</td></tr>');
				moBoTable.append('<tr><td>FormFactor</td><td>' + response.data.formFactor + '</td></tr>');
			}
		});
	}
}

function setGpu() {
	var gpuId = $('#gpu option:selected').attr('id');
	var gpuTable = $('#gpuTable');
	var pciTable = $('#pciTable');
	gpuTable.empty();
	pciTable.empty();
	if (gpuId !== undefined) {
		$.ajax({
			url: "/api/gpus/byId?id=" + gpuId,
			method: "GET",
			contentType: "application/json",
			dataType: "json",
			success: function (response) {
				pciTable.append('<tr><td>PCI-e version</td><td>' + response.data.pci + '</td></tr>');
				gpuTable.append('<tr><td>Company</td><td>' + response.data.company + '</td></tr>');
				gpuTable.append('<tr><td>Name</td><td>' + response.data.name + '</td></tr>');
				gpuTable.append('<tr><td>TDP</td><td>' + response.data.tdp + '</td></tr>');
			}
		});
	}
}

function send(userId) {
	let body = {
		account: userId,
		cpu: $('#cpu option:selected').attr('id'),
		mobo: $('#mobo option:selected').attr('id'),
		gpu: $('#gpu option:selected').attr('id')
	};
	$.ajax({
		url: "/api/setups",
		method: "POST",
		data: JSON.stringify(body),
		contentType: "application/json",
		dataType: "json",
		complete: function (response) {
			location.reload();
		}
	});
}
