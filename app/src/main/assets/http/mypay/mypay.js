var agt = navigator.userAgent.toLowerCase();
var is_NN = (navigator.appName.indexOf("Netscape") != -1);  //checking for netscape
var is_gecko = (agt.indexOf("gecko") != -1);  //checking for netscape 6.0/6.1
var is_IE = (agt.indexOf("msie") != -1);  //checking for IE
var is_IE6X = ((is_IE) && (agt.indexOf("msie 6") != -1));
var is_IE5X = ((is_IE) && (agt.indexOf("msie 5") != -1));
var is_IE4 = ((is_IE) && (agt.indexOf("msie 5") == -1) && (parseInt(navigator.appVersion) == 4));
var is_W3C = ((is_IE6X) || (is_IE5X) || (is_gecko));
var is_NN4 = ((is_NN) && (document.layers) && (!is_gecko));
var is_IE5Plus = ((is_IE) || (is_IE6X))
var nm = navigator.appName + navigator.appVersion;
var good_ID = 1;
var ssntimer;  //SSN and PIN clearing timer - This is for Form1
var ssntimervalue = 300000;  //interval for ssn timer indicates how long before inactivity clears SSN and PIN - This is for Form1
var ssntimer2;  //SSN and PIN clearing timer - This is for Form1
var ssntimervalue2 = 4000;  //interval for ssn timer indicates how long before inactivity clears SSN and PIN - This is for Form1


function SetLayer() {
    try {
        document.Form1.visLogin.focus();
    }
    catch (err) {
        //Handle errors here
    }
}

function winopen(url, a) {
    var sWidth = screen.availWidth - 5;
    var sHeight = screen.availHeight - 50;
    var features = 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,top=0,left=0,height=' + sHeight + ',width=' + sWidth
    newwin = window.open(url, a, features)
    document.Form1.visPin.value = '';
    document.Form1.visLogin.value = '';
}

function pinopen() {
    var sWidth = screen.availWidth - 5;
    var sHeight = screen.availHeight - 50;
    var features = 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,top=0,left=0,height=' + sHeight + ',width=' + sWidth
    newwin = window.open("pinletter.aspx", "mpeex", features)
    document.Form1.visPin.value = '';
    document.Form1.visLogin.value = '';
}

function cacopen(url, a) {
    var sWidth = screen.availWidth - 5;
    var sHeight = screen.availHeight - 50;
    var features = 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,top=0,left=0,height=' + sHeight + ',width=' + sWidth
    //alert("url=" + url)
    newwin = window.open(url, a, features)
    //document.forms[0].__VIEWSTATE.name = 'NOVIEWSTATE';
    //document.forms[0].__VIEWSTATE.value = '';
    //document.Form1.action = url
    //document.Form1.submit();
    //document.Form1.visPin.value = '';
    //document.Form1.visLogin.value = '';

    document.forms[1].__VIEWSTATE.name = 'NOVIEWSTATE';
    document.forms[1].__VIEWSTATE.value = '';
    var vsmartemail = getQueryVariable("FLPS")
    document.getElementById('visSmartEmail').value = vsmartemail;
    document.forms[1].action = url
    document.forms[1].submit();
    document.getElementById('visPin').value = "";
    document.getElementById('visLogin').value = "";
}


function Is9DigitsLong(IDstr) {
    //alert("Is9DigitsLong");
    var nm = "";
    if (IDstr.length == 9) {
        nm = navigator.appName + navigator.appVersion;
        if (ssntimervalue + '' == 'undefined') {
            //netscape 4 bug doesn't load ssntimervalue
            ssntimervalue = 300000;
        }
        ssntimer = setTimeout(clearSSN, ssntimervalue);

        nm = navigator.userAgent + ':' + navigator.appName + ':' + navigator.appVersion;
        if (nm.indexOf('Safari') < 0) {
            var holdfocus = document.Form1.visFocus.value
            document.getElementById(holdfocus).focus();
        }
    }
}

function clearSSN() {
    document.Form1.visPin.value = '';
    document.Form1.visLogin.value = '';
    if (ssntimer2 != null) {
        clearTimeout(ssntimer2);
        ssntimer2 = null;
    }
}

function clearSSN2() {
    document.Form1.visPin.value = '';
    document.Form1.visLogin.value = '';
    if (ssntimer2 != null) {
        clearTimeout(ssntimer2);
        ssntimer2 = null;
    }
}

function clearmSSN2() {
    document.Form1.mvisPin.value = '';
    document.Form1.mvisLogin.value = '';
    if (ssntimer2 != null) {
        clearTimeout(ssntimer2);
        ssntimer2 = null;
    }
}


function PodClick() {
    document.forms[0].__VIEWSTATE.name = 'NOVIEWSTATE';
    document.forms[0].__VIEWSTATE.value = '';
    document.Form1.action = "PinLetter.aspx";
    winopen("PinLetter.aspx", "mpeex")
    return false;
}

function GoClick(vForm, vPay, vAction) {
    PodClick();
}


//******************************************* VERIFYCLICK *****************************************************
//Verify Click is function that evaluates whether Login ID is valid, opens second window and submits to next page
function verifyClick() {
    //var vcontinue = confirm("LES for DJMS is not available. \nW2s for DCPS are not available.  \n\nSelect Cancel if this is what you wanted to view.  Select OK to continue.")
    //returns true or false

    //if (vcontinue==false)
    //	{document.Form1.visSsn.focus()
    //	return false;}

    //var RegExpInteger = /^((\+|-)\d)?\d*$/;
    var RegExpInteger = /^\d{1,9}$/;
    var RegExpAlnum = /^(\w|\d)(\w|\d)*$/;
    var RegExpLetterOrInteger = /^(\w|\d)\d*$/;
    var nm = navigator.userAgent + ':' + navigator.appName + ':' + navigator.appVersion;
    var vssn;
    var vlogin;

    if (nm.indexOf('Safari') < 0) {
        //vssn = document.Form1.visSsn.value;
        //vlogin = document.Form1.visLogin.value;
        vlogin = document.getElementById('visLogin').value;
    }
    else {
        // vssn = document.getElementById('visSsn').value;
        vlogin = document.getElementById('visLogin').value;


        //Safari apparently advances to the next line
        //before completing getElementById -- the loop
        //gives it just a moment to catch up
        //it's an ugly hack, but it works.
        for (i = 0; i < 1000; i++) {
            var s = '';
        }
    }

    //alert("vssn=" + vssn)
    //alert("vlogin=" + vlogin)

    // if vlogin is 9 numbers, reject it
    if (vlogin.length == 9 && RegExpInteger.test(vlogin)) {
        if (vlogin.substring(0, 8) == "99999999")
        { }
        else {
            alert('Your ssn cannot be your Login ID.  \n\n If you are a new customer or have not established a Login ID, \nplease click on the "Create an Account" link. ')
            document.Form1.visLogin.value = '';
            return false;
        }
    }

    // start checks of vlogin if length > 0
    if (vlogin.length > 0) {

        // Login ID can only contain numbers, letters, and certain special characters
        var RegExpAlNumSp = /^([0-9a-zA-Z\-_@\.\'])+$/;
        if (!RegExpAlNumSp.test(vlogin)) {
            alert('Your LOGIN ID can only contain letters, numbers, underscore, dash, at sign, period or apostrophe. Please try again.');
            document.Form1.visLogin.focus();
            return false;
        }

        // check length of Login ID
        if (vlogin.length < 6 || vlogin.length > 129) {
            alert("Your LOGIN ID must be 6-129 characters.");
            document.Form1.visLogin.focus();
            return false;
        }
    }
    // end of check for length of vlogin > 0

    // if neither is filled don't submit
    if (vlogin.length == 0) {
        alert("You must enter your Login ID.")
        document.Form1.visLogin.focus();
        return false;
    }

    //var pin1 = document.Form1.visPin.value;
    var pin1 = document.getElementById('visPin').value;
    // check pin1 length
    if (pin1.length > 30 || pin1.length < 8) {
        alert("You must enter 8-30 characters only for your Password.  Please try again.");
        document.Form1.visPin.focus();
        return false;
    }

    var RegExPW = /^[A-Za-z0-9#@$%^!*+=_]*$/;
    if (RegExPW.test(pin1)) {
        //alert("you passed")
    }
    else {
        alert("You have entered an invalid character in your password.  Please try again.")
        document.Form1.visPin.focus();
        document.Form1.visPin.value = "";
        return false;
    }


    document.getElementById('visSsn').value = vlogin;
    document.getElementById('visPW').value = pin1;
    //document.Form1.visSsn.value = vlogin;
    //document.Form1.visPW.value = pin1;

    //all edits good, now open the window
    var sWidth = screen.availWidth - 5;
    var sHeight = screen.availHeight - 50;
    var features = 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,top=0,left=0,height=' + sHeight + ',width=' + sWidth
    //Safari cannot redirect from one window to an already open window - ddeal
    if (nm.indexOf("Safari") == -1) {
        eex = window.open('blank.htm', 'mpeex', features);
    }

    document.forms[1].__VIEWSTATE.name = 'NOVIEWSTATE';
    document.forms[1].__VIEWSTATE.value = '';
    //document.Form1.action = "https://mypay.dfas.mil/check_login.aspx"
    //document.Form1.submit();
    //alert("I am here")

    var vsmartemail = getQueryVariable("FLPS")
    document.getElementById('visSmartEmail').value = vsmartemail;

    document.forms[0].action = "https://mypay.dfas.mil/check_login.aspx"
    document.forms[0].submit();

    if (ssntimer2 != null) {
        clearTimeout(ssntimer2);
        ssntimer2 = null;
    }
    ssntimer2 = setTimeout(clearSSN2, 4000);

    //finished all edits
    //don't want to resubmit so return false
    return false;
}


//******************************************* VERIFYVK *****************************************************
//VerifyVK is function that evaluates whether Login ID is valid, opens second window and submits to next page
function verifyVK() {
    var RegExpInteger = /^\d{1,9}$/;
    var RegExpAlnum = /^(\w|\d)(\w|\d)*$/;
    var RegExpLetterOrInteger = /^(\w|\d)\d*$/;
    var nm = navigator.userAgent + ':' + navigator.appName + ':' + navigator.appVersion;
    var vssn;
    var vlogin;

    //alert("I am in verifyVK");
    if (nm.indexOf('Safari') < 0) {
        //vssn = document.Form1.visSsn.value;
        //vlogin = document.Form1.visLogin.value;
        vlogin = document.getElementById('visLogin').value;
    }
    else {
        // vssn = document.getElementById('visSsn').value;
        vlogin = document.getElementById('visLogin').value;


        //Safari apparently advances to the next line
        //before completing getElementById -- the loop
        //gives it just a moment to catch up
        //it's an ugly hack, but it works.
        for (i = 0; i < 1000; i++) {
            var s = '';
        }
    }


    // if vlogin is 9 numbers, reject it
    if (vlogin.length == 9 && RegExpInteger.test(vlogin)) {
        if (vlogin.substring(0, 8) == "99999999")
        { }
        else {
            alert('Your ssn cannot be your Login ID.  \n\n If you are a new customer or have not established a Login ID, \nplease click on the "Create an Account" link. ')
            document.Form1.visLogin.value = '';
            return false;
        }
    }

    // start checks of vlogin if length > 0
    if (vlogin.length > 0) {

        // Login ID can only contain numbers, letters, and certain special characters
        var RegExpAlNumSp = /^([0-9a-zA-Z\-_@\.\'])+$/;
        if (!RegExpAlNumSp.test(vlogin)) {
            alert('Your LOGIN ID can only contain letters, numbers, underscore, dash, at sign, period or apostrophe. Please try again.');
            document.Form1.visLogin.focus();
            return false;
        }

        // check length of Login ID
        if (vlogin.length < 6 || vlogin.length > 129) {
            alert("Your LOGIN ID must be 6-129 characters.");
            document.Form1.visLogin.focus();
            return false;
        }
    }
    // end of check for length of vlogin > 0

    // if neither is filled don't submit
    if (vlogin.length == 0) {
        alert("You must enter your Login ID.")
        document.Form1.visLogin.focus();
        return false;
    }
    //document.Form1.visSsn.value = vlogin;
    //document.Form1.visPin.value = "";

    document.getElementById('visSsn').value = vlogin;
    document.getElementById('visPW').value = "";

    //all edits good, now open the window
    var sWidth = screen.availWidth - 5;
    var sHeight = screen.availHeight - 50;
    var features = 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,top=0,left=0,height=' + sHeight + ',width=' + sWidth
    //Safari cannot redirect from one window to an already open window - ddeal
    if (nm.indexOf("Safari") == -1) {
        eex = window.open('blank.htm', 'mpeex', features);
    }

    //document.forms[0].__VIEWSTATE.name = 'NOVIEWSTATE';
    //document.forms[0].__VIEWSTATE.value = '';
    document.forms[1].__VIEWSTATE.name = 'NOVIEWSTATE';
    document.forms[1].__VIEWSTATE.value = '';
    var vsmartemail = getQueryVariable("FLPS")
    document.getElementById('visSmartEmail').value = vsmartemail;

    //document.Form1.action = "https://mypay.dfas.mil/mypayVK.aspx"
    //document.Form1.submit();
    document.forms[0].action = https://mypay.dfas.mil/"mypayVK.aspx"
    document.forms[0].submit();

    if (ssntimer2 != null) {
        clearTimeout(ssntimer2);
        ssntimer2 = null;
    }
    ssntimer2 = setTimeout(clearSSN2, 4000);

    //finished all edits
    //don't want to resubmit so return false
    return false;
}
//******************************************* mVERIFYCLICK *****************************************************
//Verify Click is function that evaluates whether Login ID is valid, opens second window and submits to next page
function mvClick() {
    var RegExpInteger = /^\d{1,9}$/;
    var RegExpAlnum = /^(\w|\d)(\w|\d)*$/;
    var RegExpLetterOrInteger = /^(\w|\d)\d*$/;
    var nm = navigator.userAgent + ':' + navigator.appName + ':' + navigator.appVersion;
    var vssn;
    var vlogin;

    if (nm.indexOf('Safari') < 0) {
        vlogin = document.Form1.mvisLogin.value;
    }
    else {

        vlogin = document.getElementById('mvisLogin').value;


        //Safari apparently advances to the next line
        //before completing getElementById -- the loop
        //gives it just a moment to catch up
        //it's an ugly hack, but it works.
        for (i = 0; i < 1000; i++) {
            var s = '';
        }
    }

    //alert("vssn=" + vssn)
    //alert("vlogin=" + vlogin)

    // if vlogin is 9 numbers, reject it
    if (vlogin.length == 9 && RegExpInteger.test(vlogin)) {
        if (vlogin.substring(0, 8) == "99999999")
        { }
        else {
            alert('Your ssn cannot be your Login ID.  \n\n If you are a new customer or have not established a Login ID, \nplease click on the "Create an Account" link. ')
            document.Form1.mvisLogin.value = '';
            return false;
        }
    }

    // start checks of vlogin if length > 0
    if (vlogin.length > 0) {

        // Login ID can only contain numbers, letters, and certain special characters
        var RegExpAlNumSp = /^([0-9a-zA-Z\-_@\.\'])+$/;
        if (!RegExpAlNumSp.test(vlogin)) {
            alert('Your LOGIN ID can only contain letters, numbers, underscore, dash, at sign, period or apostrophe. Please try again.');
            document.Form1.mvisLogin.focus();
            return false;
        }

        // check length of Login ID
        if (vlogin.length < 6 || vlogin.length > 129) {
            alert("Your LOGIN ID must be 6-129 characters.");
            document.Form1.mvisLogin.focus();
            return false;
        }
    }
    // end of check for length of vlogin > 0

    // if neither is filled don't submit
    if (vlogin.length == 0) {
        alert("You must enter your Login ID.")
        document.Form1.mvisLogin.focus();
        return false;
    }

    var pin1 = document.Form1.mvisPin.value;
    // check pin1 length
    if (pin1.length > 30 || pin1.length < 8) {
        alert("You must enter 8-30 characters only for your Password.  Please try again.");
        document.Form1.mvisPin.focus();
        return false;
    }

    //document.Form1.visSsn.value = vlogin;
    //document.Form1.visPW.value = pin1;
    document.getElementById('visSsn').value = vlogin;
    document.getElementById('visPW').value = pin1;

    //all edits good, now open the window
    var sWidth = screen.availWidth - 5;
    var sHeight = screen.availHeight - 50;
    var features = 'toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,top=0,left=0,height=' + sHeight + ',width=' + sWidth
    //Safari cannot redirect from one window to an already open window - ddeal
    if (nm.indexOf("Safari") == -1) {
        eex = window.open('https://mypay.dfas.mil/blank.htm', 'mpeex', features);
    }

    //document.forms[0].__VIEWSTATE.name = 'NOVIEWSTATE';
    //document.forms[0].__VIEWSTATE.value = '';
    //document.Form1.action = "https://mypay.dfas.mil/check_login.aspx"
    //document.Form1.submit();

    document.forms[1].__VIEWSTATE.name = 'NOVIEWSTATE';
    document.forms[1].__VIEWSTATE.value = '';
    var vsmartemail = getQueryVariable("FLPS")
    document.getElementById('visSmartEmail').value = vsmartemail;
    document.forms[0].action = "https://mypay.dfas.mil/check_login.aspx"
    document.forms[0].submit();

    if (ssntimer2 != null) {
        clearTimeout(ssntimer2);
        ssntimer2 = null;
    }
    ssntimer2 = setTimeout(clearmSSN2, 4000);

    //finished all edits
    //don't want to resubmit so return false
    return false;
}

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    query = query.toUpperCase()
    //alert( query)
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    //alert('Query Variable ' + variable + ' not found');
}

