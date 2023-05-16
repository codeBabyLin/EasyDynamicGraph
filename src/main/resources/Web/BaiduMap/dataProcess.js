
function addOptions(){
//input type="submit" value="提交" onclick="getMessage()"
for (let m of nodes){
var value = m[0]
document.getElementById("startNode").options.add(new Option(value,value))
document.getElementById("endNode").options.add(new Option(value,value))
}
}

function onQuery(){
//alert("hahhahahah")
var select1 = document.getElementById("startNode")
var index1 = select1.selectedIndex
var value1 = select1.options[index1].text

var select2 = document.getElementById("endNode")
var index2 = select2.selectedIndex
var value2 = select2.options[index2].text

var version =  document.getElementById("version").value

console.log(value1)
console.log(value2)
console.log(version)

markPath(value1,value2,version)

}

var lastline = null;
async function markPath(s,e,v){

await callPath(s,e,v)

if(lastline!=null) {
	map.removeOverlay(lastline)
	lastline = null
}

var pps = []
//console.log(paths.length)
for(let p of paths){
  var np = namePoints[p]
  pps.push(np)
 
  }
  var polyline = new BMap.Polyline(pps, {strokeColor:"red", strokeWeight:2, strokeOpacity:0.5});
  lastline = polyline;
 // playTrack(pps);
map.addOverlay(polyline);
}

function playTrack(pps){
	
	
/* 	var trackAni = new BMapGLLib.TrackAnimation(map,polyline, {
    overallView: true, // 动画完成后自动调整视野到总览
    tilt: 30,          // 轨迹播放的角度，默认为55
    duration: 20000,   // 动画持续时长，默认为10000，单位ms
    delay: 3000        // 动画开始的延迟，默认0，单位ms
});
	 */
	var keyFrames = []
	for(let point of pps){
		var e = {center:point,tilt: 60,zoom:18}
		keyFrames.push(e)
	}
	var opts = {duration:10000, delay:3000, interation: 2}
	var effect = new AnimationEffect()
	var animation = new Animation(keyFrames,effect)
	map.startViewAnimation(animation);
}

