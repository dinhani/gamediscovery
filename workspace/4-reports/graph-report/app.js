// charts
const process = require('process');
const barChart = require('./charts/bar.json');
const clone = require('clone')

// express
const express = require('express');
const app = express();
app.set('view engine', 'jade')
app.use('/assets', express.static('node_modules'));

// neo4j
const neo4j = require('neo4j-driver').v1;
const driver = neo4j.driver(process.env.GD_NEO4J_CONN_BOLT, neo4j.auth.basic(process.env.GD_NEO4J_CONN_USER, process.env.GD_NEO4J_CONN_PASSWORD));
var session = driver.session();

// routes
// =============================================================================
// INDEX
// =============================================================================
app.get('/', function (req, res) {
  session.run("CALL db.labels()")
    .then(function(result){
      // set labels
      var labels = []
      result.records.forEach(function(row){
        labels.push(row._fields[0])
      })

      // render
      res.render('index', {labels: labels})
    })
})

// =============================================================================
// ENTRIES BY LABEL
// =============================================================================
app.get('/gamesByTypeEntry', function (req, res) {
  // parse params
  const label = req.query["type"]

  // execute query
  session.run("MATCH (target:" + label + ")-[]-(game:Game) RETURN target.name as name, count(game) as value ORDER BY value ASC")
    .then(function(result){
      // process chart
      var data = clone(barChart)
      data.height = (result.records.length * 40);

      result.records.forEach(function(row) {
        data.yAxis[0].data.push(row._fields[0])
        data.series[0].data.push(row._fields[1].low)
      });

      // render
      let dataAsString = JSON.stringify(data);
      let title = "Games by " + label;
      let height = data.height + 200;

      res.render('chart_echarts', {title: title, data: dataAsString, height: height})
    })
});

app.listen(3002, function(){
  console.log("Running on 3002");
});
