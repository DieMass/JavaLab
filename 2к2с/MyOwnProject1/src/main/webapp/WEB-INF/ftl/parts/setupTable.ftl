<div class="container">
    <div class="col align-self-center">
        <table class="table table-bordered">
            <thead>
            <tr style="background: #89c236; color: #ffffff; text-align: center">
                <th>Cpu</th>
                <th>MotherBoard</th>
                <th>Gpu</th>
            </tr>
            </thead>
            <tbody>
            <#list setups as setup>
                <tr>
                    <td>${setup.cpu.family.name} ${setup.cpu.line.name} ${setup.cpu.series}</td>
                    <td>${setup.motherBoard.name}</td>
                    <td>${setup.gpu.name}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>