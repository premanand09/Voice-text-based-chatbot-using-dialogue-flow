var apiClient = new ApiAi.ApiAiClient({accessToken: '368c815420d44a05b1779829355708d8'});

function isChrome() {
  var isChromium = window.chrome,
    winNav = window.navigator,
    vendorName = winNav.vendor,
    isOpera = winNav.userAgent.indexOf("OPR") > -1,
    isIEedge = winNav.userAgent.indexOf("Edge") > -1,
    isIOSChrome = winNav.userAgent.match("CriOS");

  if(isIOSChrome){
    return true;
  } else if(isChromium !== null && isChromium !== undefined && vendorName === "Google Inc." && isOpera == false && isIEedge == false) {
    return true;
  } else {
    return false;
  }
}


  
function gotoListeningState() {
  const micListening = document.querySelector(".mic .listening");
  const micReady = document.querySelector(".mic .ready");

  micListening.style.display = "block";
  micReady.style.display = "none";
}

function gotoReadyState() {
  const micListening = document.querySelector(".mic .listening");
  const micReady = document.querySelector(".mic .ready");

  micListening.style.display = "none";
  micReady.style.display = "block";
}

function addBotItem(text) {
  const appContent = document.querySelector(".app-content");
  
  
  appContent.innerHTML += '<div class="item-container item-container-bot"><div class="item"><p>' + text + '</p></div></div>';
  
  appContent.scrollTop = appContent.scrollHeight; // scroll to bottom
}


function download_sr_report() {
  const appContent = document.querySelector(".app-content");
  

  
  appContent.innerHTML += '<div class="item-container"><div class="report"><a href="/v/ShipmentDelayReport.xlsx" download="ShipmentDelayReport.xlsx"><img border="0" src="/v/sr_image.jpg" alt="W3Schools" width="104" height="142" style="border-color: red"> </a></div></div>';
  
  appContent.scrollTop = appContent.scrollHeight; // scroll to bottom
}

function download_report() {
  const appContent = document.querySelector(".app-content");
  

  
  appContent.innerHTML += '<div class="item-container"><div class="report"><a href="/v/Data_Quality.twbx" download="Data_Quality.twbx"><img border="0" src="/v/AAPL.png" alt="W3Schools" width="104" height="142" style="border-color: red"> </a></div></div>';
  
  appContent.scrollTop = appContent.scrollHeight; // scroll to bottom
}

function Job_Table_create(text) {
//alert();
if (document.getElementById("failTable")) {
	
	var elem = document.getElementById("failTable");
	elem.parentNode.removeChild(elem);



}

var table = document.createElement("TABLE");
    table.border = "1";
	
	 var columnCount = 3;
	var row = table.insertRow(-1);
	   var headers = new Array();
    headers.push(["Job_name", "status","rows"]);
	
	 for (var i = 0; i < columnCount; i++) {
        var headerCell = document.createElement("TH");
        headerCell.innerHTML = headers[0][i];
        row.appendChild(headerCell);
    }
	
	
	  text["listofdata"].forEach(function(message) {
	
    var Job_name = message["job_name"];
	var status = message["status"];
	var rows = message["rows"];
	
	//alert(report_name);
	
	row = table.insertRow(-1);
	var cell1 = row.insertCell(-1);
	cell1.innerHTML = Job_name;
	
	var cell2 = row.insertCell(-1);
	cell2.innerHTML = status;
	
	var cell3 = row.insertCell(-1);
	cell3.innerHTML = rows;
	
	  });
	


	
const appContent = document.querySelector(".app-content");
  
  appContent.innerHTML += '<div class="item-container"><div id="failTable"></div></div>';
  var dvTable = document.getElementById("failTable");
    dvTable.innerHTML = "";
    dvTable.appendChild(table);
  dvTable.appendChild(table)
  
  appContent.scrollTop = appContent.scrollHeight; // scroll to bottom
}

function Table_create(text) {
//alert();
if (document.getElementById("failTable")) {
	
	var elem = document.getElementById("failTable");
	elem.parentNode.removeChild(elem);



}

var table = document.createElement("TABLE");
    table.border = "1";
	
	 var columnCount = 2;
	var row = table.insertRow(-1);
	   var headers = new Array();
    headers.push(["report_name", "status"]);
	
	 for (var i = 0; i < columnCount; i++) {
        var headerCell = document.createElement("TH");
        headerCell.innerHTML = headers[0][i];
        row.appendChild(headerCell);
    }
	
	
	  text["listofdata"].forEach(function(message) {
    var report_name = message["report_name"];
	var status = message["status"];
	
	//alert(report_name);
	
	row = table.insertRow(-1);
	var cell1 = row.insertCell(-1);
	cell1.innerHTML = report_name;
	
	var cell2 = row.insertCell(-1);
	cell2.innerHTML = status;
	
	  });
	


	
const appContent = document.querySelector(".app-content");
  
  appContent.innerHTML += '<div class="item-container"><div id="failTable"></div></div>';
  var dvTable = document.getElementById("failTable");
    dvTable.innerHTML = "";
    dvTable.appendChild(table);
  dvTable.appendChild(table)
  
  appContent.scrollTop = appContent.scrollHeight; // scroll to bottom
}


function addUserItem(text) {
	
	
  const appContent = document.querySelector(".app-content");
  appContent.innerHTML += '<div class="item-container item-container-user"><div class="item"><p>' + text + '</p></div></div>';
  
  appContent.scrollTop = appContent.scrollHeight; // scroll to bottom
  
if (document.getElementById("item1")) {
	

	var elem = document.getElementById("item1");
	elem.parentNode.removeChild(elem);



}
  
}

function addBotItemRichMessage(text) {
  
  const appContent = document.querySelector(".app-content");
  appContent.innerHTML += '<div class="item-container1 item-container-bot1"><div class="item1" id="item1" onclick="blurSelectText()"><p>' + text + '</p></div></div>';
  appContent.scrollTop = appContent.scrollHeight; // scroll to bottom
};


function blurSelectText() {
	//alert('hi');
    document.getElementById("item1").style.backgroundColor = "#DCDCDC";
    document.getElementById("item1").style.boxShadow = "none";

	var selectedText = document.getElementById("item1").getElementsByTagName('p')[0].innerHTML;
	document.getElementById("item1").style.pointerEvents = "none";	
	
	$("#item1").unbind('click');
	
	addUserItem(selectedText);
	myFunc(selectedText);
	};
	
	function myFunc(selectedText){
		//const client = new ApiAi.ApiAiClient({accessToken: '368c815420d44a05b1779829355708d8'});
		//alert(selectedText);
const promise = apiClient.textRequest('yes');

promise
    .then(handleResponse)
    .catch(handleError);

function handleResponse(serverResponse) {
	//alert('response');
	
	
	var aiSpeech='';
	var richMessage = null; 
	
      var messages = serverResponse["result"]["fulfillment"]["messages"];
	 
	  messages.forEach(function(message) {
    var speech = message.speech;
	
	aiSpeech=aiSpeech + speech+"\n"
  
});
	  
      var msg = new SpeechSynthesisUtterance(aiSpeech);
      addBotItem(aiSpeech);
      
	 
      window.speechSynthesis.speak(msg);
	  
	
        console.log(serverResponse);
}
function handleError(serverError) {
        console.log(serverError);
}


	};


function displayCurrentTime() {
  const timeContent = document.querySelector(".time-indicator-content");
  const d = new Date();
  const s = d.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});
  timeContent.innerHTML = s;
}

function addError(text) {
  addBotItem(text);
  const footer = document.querySelector(".app-footer");
  footer.style.display = "none";
}



	
	
document.addEventListener("DOMContentLoaded", function(event) {

  

	
  // test for relevant API-s
  // for (let api of ['speechSynthesis', 'webkitSpeechSynthesis', 'speechRecognition', 'webkitSpeechRecognition']) {
  //   console.log('api ' + api + " and if browser has it: " + (api in window));
  // }


  displayCurrentTime();

  // check for Chrome
  if (!isChrome()) {
    addError("This demo only works in Google Chrome.");
    return;
  }

  if (!('speechSynthesis' in window)) {
    addError("Your browser doesn’t support speech synthesis. This demo won’t work.");
    return;
  }

  if (!('webkitSpeechRecognition' in window)) {
    addError("Your browser cannot record voice. This demo won’t work.");
    return;
  }

  // Now we’ve established that the browser is Chrome with proper speech API-s.

  // api.ai client
  

  // Initial feedback message.
  addBotItem("Hi! I’m HP Hawkeye BOT. Tap the microphone and start talking to me.");

	startTextRecognition();
	
	
  var recognition = new webkitSpeechRecognition();
  var recognizedText = null;
  recognition.continuous = false;
  recognition.onstart = function() {
    recognizedText = null;
  };
  
  
  recognition.onresult = function(ev) {
    
	recognizedText = ev["results"][0][0]["transcript"];

	addUserItem(recognizedText);
	
    ga('send', 'event', 'Message', 'add', 'user');

	
    let promise = apiClient.textRequest(recognizedText);

    promise
        .then(handleResponse)
        .catch(handleError);

    function handleResponse(serverResponse) {

	if(serverResponse["result"]["metadata"]["intentName"].includes("failed reports")){
		
		var report = serverResponse["result"] ["fulfillment"] ["data"];
		
		Table_create(report);
		
	}

		if(serverResponse["result"]["metadata"]["intentName"].includes("show tableau reports")){
		
		
		download_report();		

		
	}

		if(serverResponse["result"]["metadata"]["intentName"].includes("Job_Status_success")){
		

		var report = serverResponse["result"] ["fulfillment"] ["data"];

		Job_Table_create(report);	

		
	}

	
      // Set a timer just in case. so if there was an error speaking or whatever, there will at least be a prompt to continue
      var timer = window.setTimeout(function() { startListening(); }, 5000);
	var aiSpeech='';
      var messages = serverResponse["result"]["fulfillment"]["messages"];

	  var richMessage=null;
	  messages.forEach(function(message) {
    var speech = message.speech;
	
	
    if(speech.includes("What is the POD link for this shipment ?")){
		
		richMessage = speech;
	aiSpeech=aiSpeech +"\n"

		}
		else if(speech.includes("Send this DMR report in an email")){
		
		richMessage = speech;
	aiSpeech=aiSpeech +"\n"

		}
 	else if(serverResponse["result"]["metadata"]["intentName"].includes("sr_report_download") && speech.includes("click on above image to download sense and respond report")){
		
		download_sr_report();		
	aiSpeech=aiSpeech + speech+"\n"

		} 
	
	else{
	aiSpeech=aiSpeech + speech+"\n"

	}
  
});
	  
      var msg = new SpeechSynthesisUtterance(aiSpeech);
      addBotItem(aiSpeech);
      
	  ga('send', 'event', 'Message', 'add', 'bot');
      msg.addEventListener("end", function(ev) {
        window.clearTimeout(timer);
        startListening();
      });
      msg.addEventListener("error", function(ev) {
        window.clearTimeout(timer);
        startListening();
      });

      window.speechSynthesis.speak(msg);
	  
    if(richMessage.includes("What is the POD link for this shipment ?")){
		
	  var msg1 = new SpeechSynthesisUtterance(richMessage);
      addBotItemRichMessage(richMessage);
   
     ga('send', 'event', 'Message', 'add', 'bot');
      msg1.addEventListener("end", function(ev) {
        window.clearTimeout(timer);
        startListening();
      });
      msg1.addEventListener("error", function(ev) {
        window.clearTimeout(timer);
        startListening();
      });

      //window.speechSynthesis.speak(msg1);
   
	}


    if(richMessage.includes("Send this DMR report in an email")){
		
	  var msg1 = new SpeechSynthesisUtterance(richMessage);
      addBotItemRichMessage(richMessage);
   
     ga('send', 'event', 'Message', 'add', 'bot');
      msg1.addEventListener("end", function(ev) {
        window.clearTimeout(timer);
        startListening();
      });
      msg1.addEventListener("error", function(ev) {
        window.clearTimeout(timer);
        startListening();
      });

      //window.speechSynthesis.speak(msg1);
   
	}



    }
    function handleError(serverError) {
      console.log("Error from api.ai server: ", serverError);
    }
  };

  recognition.onerror = function(ev) {
    console.log("Speech recognition error", ev);
  };
  recognition.onend = function() {
    gotoReadyState();
  };

  function startListening() {
    gotoListeningState();
    recognition.start();
  }

  const startButton = document.querySelector("#start");
  startButton.addEventListener("click", function(ev) {
    ga('send', 'event', 'Button', 'click');
    startListening();
    ev.preventDefault();
  });

  // Esc key handler - cancel listening if pressed
  // http://stackoverflow.com/questions/3369593/how-to-detect-escape-key-press-with-javascript-or-jquery
  document.addEventListener("keydown", function(evt) {
    evt = evt || window.event;
    var isEscape = false;
    if ("key" in evt) {
        isEscape = (evt.key == "Escape" || evt.key == "Esc");
    } else {
        isEscape = (evt.keyCode == 27);
    }
    if (isEscape) {
        recognition.abort();
    }
  });


  function startTextRecognition(){
	  
  document.getElementById("userinput").addEventListener("keypress", function(event) {
    if(event.which == 13) {
		
		
		var userText = document.getElementById("userinput").value;

	addUserItem(userText);
	event.preventDefault();
	
document.getElementById("userinput").value='';
	
	

        // TRIGGER YOUR FUNCTION
    
	    let promise = apiClient.textRequest(userText);

    promise
        .then(handleResponse)
        .catch(handleError);

    function handleResponse(serverResponse) {

	if(serverResponse["result"]["metadata"]["intentName"].includes("failed reports")){
		
		var report = serverResponse["result"] ["fulfillment"] ["data"];

		Table_create(report);
		
	}


		if(serverResponse["result"]["metadata"]["intentName"].includes("show tableau reports")){
		
		
		download_report();		

		
	}

		if(serverResponse["result"]["metadata"]["intentName"].includes("Job_Status_success")){
		

		var report = serverResponse["result"] ["fulfillment"] ["data"];

		Job_Table_create(report);	

		
	}

	
	var aiSpeech='';
      var messages = serverResponse["result"]["fulfillment"]["messages"];
	  var richMessage=null;
	  messages.forEach(function(message) {
    var speech = message.speech;
	
    if(speech.includes("What is the POD link for this shipment ?")){
		
		richMessage = speech;
	aiSpeech=aiSpeech +"\n"

		}
		else if(speech.includes("Send this DMR report in an email")){
		
		richMessage = speech;
	aiSpeech=aiSpeech +"\n"

		}
 	else if(serverResponse["result"]["metadata"]["intentName"].includes("sr_report_download") && speech.includes("click on above image to download sense and respond report")){
		
		download_sr_report();		
	aiSpeech=aiSpeech + speech+"\n"

		} 
	
	else{
	aiSpeech=aiSpeech + speech+"\n"

	}
  
});
	  
      var msg = new SpeechSynthesisUtterance(aiSpeech);
      addBotItem(aiSpeech);
      
	 
      window.speechSynthesis.speak(msg);
	  
    if(richMessage.includes("What is the POD link for this shipment ?")){
		
	  var msg1 = new SpeechSynthesisUtterance(richMessage);
      addBotItemRichMessage(richMessage);
   

    //  window.speechSynthesis.speak(msg1);
   //addBotItemClickMessageYes("Yes");
   //addBotItemClickMessageNo("No");
   
   //startButtonClickEvent();
  
	}
   
    if(richMessage.includes("Send this DMR report in an email")){
		
	  var msg1 = new SpeechSynthesisUtterance(richMessage);
      addBotItemRichMessage(richMessage);
   

    //  window.speechSynthesis.speak(msg1);
   //addBotItemClickMessageYes("Yes");
   //addBotItemClickMessageNo("No");
   
   //startButtonClickEvent();
  
	}
   

	}
    function handleError(serverError) {
      console.log("Error from api.ai server: ", serverError);
    }
	
	
	}
});
  };


  
 function startButtonClickEvent(){
	   //get a reference to the element
  
  $(".item-container-click #Yes").click(function(){
	var userText = $('.item-container-click #Yes').val();

		
	addUserItem(userText);
	event.preventDefault();

        // TRIGGER YOUR FUNCTION
    
	apiClickRequest(userText);
	
	
	});

  
  $(".item-container-click #No").click(function(){
	var userText = $('.item-container-click #No').val();
		
	addUserItem(userText);
	event.preventDefault();

        // TRIGGER YOUR FUNCTION
    
	apiClickRequest(userText);
	
	
	});



  };
  


  
});