
var nodes = []
var relations = []
var paths = []
async function getNodes(){
	var driver = neo4j.driver('bolt://localhost:7687',neo4j.auth.basic('neo4j','neo4j'))
	var session = driver.session()
	var {records, summary} = await driver.executeQuery('match(n) return n.name as name,n.latitude as lat, n.longitude as lon')
	for (let record of records){
		var name = record.get('name')
		var lat = record.get('lat')
		var lon = record.get('lon')
		var e = [name,lat,lon]
		nodes.push(e)
		console.log(e)
	}

}


async function getRelations(){
	var driver = neo4j.driver('bolt://localhost:7687',neo4j.auth.basic('neo4j','neo4j'))
	var session = driver.session()
	var {records, summary} = await driver.executeQuery('match(n)-[r]->(m) return n.name as nn,m.name as mn')
	for (let record of records){
		var name1 = record.get('nn')
		var name2 = record.get('mn')
	
		var e = [name1,name2]
		relations.push(e)
		console.log(e)
	}

}

async function logPrint(){
	console.log(nodes.length)
	await getNode()
	console.log(nodes.length)
	await getRelations()
	console.log(relations.length)
}

async function callShortestPath(){
	var driver = neo4j.driver('bolt://localhost:7687',neo4j.auth.basic('neo4j','neo4j'))
	var session = driver.session()
	var cypher = "MATCH (start:City{name:'天津'}), (end:City{name:'海口'}) " +
                        "CALL codebaby.shortestPath.astar.streamWithName(start, end, 'cost',9) " +
                        "YIELD name, cost RETURN name, cost "
	var {records, summary} = await driver.executeQuery(cypher)
	for (let record of records){
		//var name1 = record.get('nn')
		//var name2 = record.get('mn')
		//var lat = record.get('lat')
		//var lon = record.get('lon')
		//var id = record.get('n')
		//var e = [name1,name2]
		//relations.push(e)
		var name = record.get('name')
		var cost = record.get('cost')
		paths.push(name)
		console.log(name)
		console.log(cost)
		//console.log(record)
	}

}

async function callPath(s,e,v){
	var driver = neo4j.driver('bolt://localhost:7687',neo4j.auth.basic('neo4j','neo4j'))
	var session = driver.session()
	var q1 = `MATCH (start:City{name:'${s}'}), (end:City{name:'${e}'})`
	var q2 = `CALL codebaby.shortestPath.astar.streamWithName(start, end, 'cost',${v})`
	var cypher = q1 + q2
                     +
                    "YIELD name, cost RETURN name, cost "
	var {records, summary} = await driver.executeQuery(cypher)
	paths = []
	for (let record of records){
		
		var name = record.get('name')
		var cost = record.get('cost')
		paths.push(name)
		console.log(name)
		console.log(cost)
		//console.log(record)
	}
}