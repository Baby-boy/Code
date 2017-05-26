var uploadCode = "";
var imagesName = "";
var xmlHttpRequest;
var _fileUrl = "http://gcj.oss-cn-shanghai.aliyuncs.com/";

//XmlHttpRequest对象  
function createXmlHttpRequest() {
    if (window.ActiveXObject) { //如果是IE浏览器
        return new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) { //非IE浏览器
        return new XMLHttpRequest();
    }
}
var POLICY_JSON = {
    "expiration": "2020-12-01T12:00:00.000Z",
    "conditions": [
        ["starts-with", "$key", ""],
        {"bucket": 'gcj'},
        ["starts-with", "$Content-Type", ""],
        ["content-length-range", 0, 524288000]
    ]
};
var secret = 'ntsuVqQ06r4B46m31g61RnLUQsBGkE';
var policyBase64 = Base64.encode(JSON.stringify(POLICY_JSON));
var signature = b64_hmac_sha1(secret, policyBase64);

var backFunction = null;
var jdt = null;
var name = "";

//tag(标签)，f(path,name)上传成功（网络访问路径，文件名），jdt(percentComplete)进度条（进度条百分比）
function uploadFile(tag,f,j) {
	if(f != null){
		backFunction = f;
	}
	if(j != null){
		jdt = j;
	}
    var file = tag.files[0];
    var fd = new FormData();
    name = file.name;
    imagesName = guid() + suffix(file.name);
    var key = imagesName;
    
    fd.append('key', key);
    fd.append('Content-Type', file.type);
    fd.append('OSSAccessKeyId', 'LTAIr1w3JfMw6V3g');
    fd.append('policy', policyBase64)
    fd.append('signature', signature);
    fd.append("file", file);
    var xhr = createXmlHttpRequest()
    xhr.upload.addEventListener("progress", uploadProgress, false);
    xhr.addEventListener("load", uploadComplete, false);
    xhr.addEventListener("error", uploadFailed, false);
    xhr.addEventListener("abort", uploadCanceled, false);

    xhr.open('POST', 'https://gcj.oss-cn-shanghai.aliyuncs.com', true);
    xhr.send(fd);
}

function uploadProgress(evt) {
    if (evt.lengthComputable) {
        var percentComplete = Math.round(evt.loaded * 100 / evt.total);
        if(jdt != null){
        	jdt(percentComplete);
        }
    }
}

function uploadComplete(evt) {
	var path = _fileUrl + imagesName;
	backFunction(path,name);
	console.log(_fileUrl + imagesName);
}

function uploadFailed(evt) {
    alert("There was an error attempting to upload the file." + evt);
}

function uploadCanceled(	) {
    alert("The upload has been canceled by the user or the browser dropped the connection.");
}
