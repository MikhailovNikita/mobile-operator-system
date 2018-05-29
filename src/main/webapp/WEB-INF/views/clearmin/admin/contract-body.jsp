<div class="panel-body" id="contract-container">
    <h3 id="contract_number">Number: </h3>
    <span id="contract_status"></span>
    <a id="contract_date" class="text-muted">Created on: </a><br>
    <h4 id="contract_payment"></h4>
    <h4 id="contract_tariff">Tariff: </h4>

    <table class="table table-hover" style="width: 50%">
        <caption>Enabled options:</caption>
        <thead>
        <tr>
            <th>Name</th>
            <th>Cost</th>
            <th>Access cost</th>
            <th style="width: 10%">Action</th>
        </tr>
        </thead>
        <tbody id="options_table_body">

        </tbody>
    </table>
    <h5 id="contract-cost">Total cost: </h5>
    <hr>
    <h4>Edit contract:</h4>
    <form onsubmit="return changeTariff()">
        <div class="form-group">
            <label for="tariffs">Tariff</label>
            <select class="form-control" id="tariffs" style="width: 25%">
            </select>
            <button type="submit" class="btn btn-primary">Change</button>
        </div>
    </form>

    <form onsubmit="return enableOption()">
        <div class="form-group">
            <label for="tariffs">Option</label>
            <select class="form-control" id="options" style="width: 25%">
            </select>
            <button type="submit" class="btn btn-primary">Enable</button>
        </div>
    </form>
</div>