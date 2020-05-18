<!DOCTYPE html>
<#import "spring.ftl" as spring />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${user.name}'s Homepage</title>
    <#--    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"-->
    <#--          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">-->
    <link rel="stylesheet" href="<@spring.url '/css/setups.css'/>">
    <script src="<@spring.url '/js/setups.js'/>"/>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="></script>
    <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js'></script>
</head>
<body onload="getCpus(''); getMoBos('', ''); getGpus('');" style="font-family: 'Montserrat', sans-serif;">
<#include "parts/header.ftl">
<div style="text-align: center">
    <span class="custom-dropdown big">
        <select title="Cpus" id="cpu" onchange="getAdapter(value, '', 'cpu'); setCpu();"></select>
    </span>
    <span class="custom-dropdown big">
            <select title="MoBo" id="mobo"
                    onchange="getAdapter(value.split(';')[0], value.split(';')[1], 'mobo'); setMoBo();"></select>
    </span>
    <span class="custom-dropdown big">
            <select title="Gpus" id="gpu" onchange="getAdapter('', value, 'gpu'); setGpu();"></select>
    </span>
    <button onclick="send(${user.id})">Отправить</button>
</div>
<div class="container">
    <div class="col align-self-center">
        <div class="cont">
            <div class="column">
                <table class="table table-bordered table-side-by-side">
                    <thead>Cpu</thead>
                    <tbody id="cpuTable">
                    </tbody>
                </table>
            </div>
            <div class="column">
                <table class="table table-bordered table-side-by-side">
                    <thead>Soket</thead>
                    <tbody id="socketTable">
                    </tbody>
                </table>
            </div>
            <div class="column">
                <table class="table table-bordered table-side-by-side">
                    <thead>MotherBoard</thead>
                    <tbody id="moBoTable">
                    </tbody>
                </table>
            </div>
            <div class="column">
                <table class="table table-bordered table-side-by-side">
                    <thead>PCI-e bus</thead>
                    <tbody id="pciTable">
                    </tbody>
                </table>
            </div>
            <div class="column">
                <table class="table table-bordered table-side-by-side">
                    <thead>Gpu</thead>
                    <tbody id="gpuTable">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<#include "parts/setupTable.ftl">
</body>
</html>