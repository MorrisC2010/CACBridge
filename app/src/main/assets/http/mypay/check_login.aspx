

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>
    Login Re-entry
</title><meta content="Microsoft Visual Studio .NET 7.1" name="GENERATOR" /><meta content="Visual Basic .NET 7.1" name="CODE_LANGUAGE" /><meta content="JavaScript" name="vs_defaultClientScript" /><meta content="http://schemas.microsoft.com/intellisense/ie5" name="vs_targetSchema" /><link href="BasePage.css" type="text/css" rel="stylesheet" />

<script language="javascript" type="text/javascript">
        var clickit = 0;
        function setClickit() {
            clickit = 1;
            document.Form1.hidEnter.value = clickit;
            return true;
        }

        function verifyIt() {
            //alert("clickit=" + clickit)
            return verifyClick();
        }

        function setFocus() {
            clickit = 0;
            document.Form1.hidEnter.value = clickit;
            //alert("clickit=" + clickit);
            if (document.Form1.txtLogin != null) {
                if (document.Form1.txtLogin.value.length > 0)
                    return true;
            }
            { document.Form1.txtLogin.focus(); }
            return true;
        }

        function verifyLink() {
            document.Form1.newPin.value = "";
            clickit = 1;
            document.Form1.hidEnter.value = clickit;
            return true;
        }

        function verifySubmit() {
            clickit = 2;
            document.Form1.hidEnter.value = clickit;
            return verifyClick();
        }

        function verifyClick() {
            var RegExpInteger = /^((\+|-)\d)?\d*$/;
            var RegExpAlnum = /^(\w|\d)(\w|\d)*$/;
            var RegExpLetterOrInteger = /^(\w|\d)\d*$/;
            var nm = navigator.userAgent + ':' + navigator.appName + ':' + navigator.appVersion;
            var vlogin;

            //alert("clickit=" + clickit);

            if (clickit == 1)
                return true;

            if (nm.indexOf('Safari') < 0) {
                vlogin = document.Form1.txtLogin.value;
            }
            else {

                vlogin = document.getElementById('txtLogin').value;


                //Safari apparently advances to the next line
                //before completing getElementById -- the loop
                //gives it just a moment to catch up
                //it's an ugly hack, but it works.
                for (i = 0; i < 1000; i++) {
                    var s = '';
                }
            }

            //alert("vlogin=" + vlogin)

            // if vlogin is 9 numbers, reject it
            if (vlogin.length == 9 && RegExpInteger.test(vlogin)) {
                alert("Your ssn cannot be your Login ID. If you need to create your Login ID for account access, use the appropriate link below. ")
                document.Form1.txtLogin.value = '';
                document.Form1.txtLogin.focus();
                return false;
            }

            // start checks of vlogin if length > 0
            if (vlogin.length > 0) {

                // Login ID can only contain numbers, letters, and certain special characters
                var RegExpAlNumSp = /^([0-9a-zA-Z\-_@\.\'])+$/;
                if (!RegExpAlNumSp.test(vlogin)) {
                    alert('Your LOGIN ID can only contain letters, numbers, underscore, dash, at sign, period or apostrophe. Please try again.');
                    document.Form1.txtLogin.focus();
                    return false;
                }

                // check length of Login ID
                if (vlogin.length < 6 || vlogin.length > 129) {
                    alert("Your LOGIN ID must be 6-129 characters.");
                    document.Form1.txtLogin.focus();
                    return false;
                }
            }
            // end of check for length of vlogin > 0


            // if neither is filled don't submit
            if (vlogin.length == 0) {
                alert("You must enter your Login ID.")
                document.Form1.txtLogin.focus();
                return false;
            }


            var pin1 = document.Form1.newPin.value;
            // check pin1 length
            if (pin1.length > 30 || pin1.length < 8) {
                alert("You must enter 8-30 characters only for your Password.  Please try again.");
                document.Form1.newPin.focus();
                return false;
            }

            var RegExPW = /^[A-Za-z0-9#@$%^!*+=_]*$/;
            if (RegExPW.test(pin1)) {
                //alert("you passed")
            }
            else {
                alert("You have entered an invalid character in your password.  Please try again.")
                document.Form1.newPin.focus();
                document.Form1.newPin.value = "";
                return false;
            }
            //alert("all is okay")
            return true;
        }


        //-->


        /*###########################################
        #                                           #
        #  VIRTUAL KEYBOARD                         #
        #                                           #
        ###########################################*/

        /*-------------------------------------------
        |                                           |
        |  DEBUG MODE                               |
        |                                           |
        -------------------------------------------*/
        // false = debug off
        //  true = debug on
        var debug_mode = false;
        /*--------------------------------------------
        |                                            |
        |  LOGIC TO GENERATE KEYBOARD                |
        |                                            |
        --------------------------------------------*/

        function VirtualKeyboard() {
            var keyorder = ""
            var holdorder = ""
            var holdfocus = ""
            var sOrder = ""
            document.write('<div id=virtual_keyboard>');
            document.write('<div class=row_of_keys>');
            keyorder = NumbersRow();
            holdorder = keyorder
            holdfocus = "vk" + keyorder.substring(1, 2)
            document.Form1.visFocus.value = holdfocus
            document.write('</div>');


            document.write('<div class=row_of_keys>');
            sOrder = SpecialCharactersRow();
            document.write('<input class=keys_special type=button onclick=PasswordBackspace() value=Backspace title="Backspace Delete" />');
            document.write('<input class=keys_special type=button onclick=PasswordClear() value=Clear title="Clear" />');
            document.write('</div>');

            document.write('<div class=row_of_keys>');
            keyorder = LettersTopRow();
            holdorder = holdorder + keyorder
            document.write('</div>');

            document.write('<div class=row_of_keys>');
            keyorder = LettersMiddleRow();
            holdorder = holdorder + keyorder
            document.write('</div>');

            document.write('<div class=row_of_keys>');
            keyorder = LettersBottomRow();
            holdorder = holdorder + keyorder
            holdorder = holdorder + sOrder
            document.write('</div>');

            document.write('<div class=row_of_keys>');
            document.write('<input class="keys_shift" type="button" onclick=ShowLower() value="Change to Lowercase" title="Lowercase" id="shift" />');
            document.write('</div>');

            //alert("keyorder=" + holdorder)
            document.Form1.visOrder.value = holdorder
            document.write('</div>');
        }

        /*--------------------------------------------
        |                                            |
        |  LOGIC TO ENTER PASSWORD VALUE             |
        |                                            |
        --------------------------------------------*/

        // PLACES THE FOCUS ON THE PASSWORD FIELD, SO
        // AS TO REMOVE THE FOCUS FROM THE BUTTON CLICKED ON.
        function clearButtonFocus() {
            var nm = navigator.userAgent + ':' + navigator.appName + ':' + navigator.appVersion;
            if (nm.indexOf('Safari') < 0) {
                var holdfocus = document.Form1.visFocus.value
                document.getElementById(holdfocus).focus();
            }
        }


        // Logic to Fill Password Value
        function PasswordVK(PasswordChar) {
            // maximum characters allowed to be entered in password field
            var maxLength = 30;
            var pinvalue = "";
            var shiftvalue = "";
            var RegExAlpha = /^[A-Za-z]*$/;

            pinvalue = document.Form1.newPin.value;
            shiftvalue = document.getElementById("shift").value;

            if (shiftvalue == "Change to Uppercase") {
                //alert(shiftvalue)
                if (RegExAlpha.test(PasswordChar)) {
                    PasswordChar = PasswordChar.toLowerCase();
                }
            }

            if (pinvalue.length < maxLength) {
                if (debug_mode == true) {
                    alert("Password Character Entered \n--------------------------------------------------\n" + PasswordChar);
                }
                pinvalue += PasswordChar;
                document.Form1.newPin.value += PasswordChar;

                if (debug_mode == true) {
                    alert("Password Current Value \n--------------------------------------------------\n" + pinvalue);
                }
            }
            else {
                // if (debug_mode == true) {
                alert("Important Message \n--------------------------------------------------\n Password Cannot Exceed Max Field Length of " + maxLength);
                //  }
            }

            // 463901/463905
            clearButtonFocus();


        }


        // Logic to address double click issue
        function PasswordVK2(PasswordChar) {
            //alert("doubleclick")
            //If IE treat like click; otherwise do nothing
            var shiftvalue = "";
            var RegExAlpha = /^[A-Za-z]*$/;
            shiftvalue = document.getElementById("shift").value;

            if (shiftvalue == "Change to Uppercase") {
                // alert(shiftvalue)
                if (RegExAlpha.test(PasswordChar)) {
                    PasswordChar = PasswordChar.toLowerCase();
                }
            }

            if (event.which == null)
            { PasswordVK(PasswordChar) }

        }


        /*--------------------------------------------
        |                                            |
        |  LOGIC TO CONTROL BACKSPACE KEY            |
        |                                            |
        --------------------------------------------*/

        // Logic to Handle Backpace Key
        function PasswordBackspace() {

            if (debug_mode == true) {
                alert("Password Before Back \n--------------------------------------------------\n" + document.Form1.newPin.value);
            }
            document.Form1.newPin.value = document.Form1.newPin.value.slice(0, document.Form1.newPin.value.length - 1);
            if (debug_mode == true) {
                alert("Password After Back \n--------------------------------------------------\n" + document.Form1.newPin.value);
            }


            // 463901/463905
            clearButtonFocus();

        }

        /*--------------------------------------------
        |                                            |
        |  LOGIC TO CONTROL CLEAR KEY                |
        |                                            |
        --------------------------------------------*/

        // Logic to Handle Clear Key
        function PasswordClear() {

            if (debug_mode == true) {
                alert("Password Before Clear \n--------------------------------------------------\n" + document.Form1.newPin.value);
            }
            document.Form1.newPin.value = "";
            if (debug_mode == true) {
                alert("Password After Clear \n--------------------------------------------------\n" + document.Form1.newPin.value);
            }


            // 463901/463905
            clearButtonFocus();

        }

        /*--------------------------------------------
       |                                            |
       |  LOGIC TO CONTROL LOWER CASE               |
       |                                            |
       --------------------------------------------*/

        // Logic to Handle Lower Case
        function ShowLower() {
            //alert("in PasswordLower")
            var shiftval = document.getElementById("shift").value
            //var arrowval = document.getElementById("arrow").value
            if (shiftval == "Change to Lowercase") {
                document.getElementById("shift").value = "Change to Uppercase"
            }
            else {
                document.getElementById("shift").value = "Change to Lowercase"
            }

            var letval;
            if (shiftval == "Change to Lowercase") {
                letval = document.getElementById("L0").value.toLowerCase()
                document.getElementById("L0").value = letval
                letval = document.getElementById("L1").value.toLowerCase()
                document.getElementById("L1").value = letval
                letval = document.getElementById("L2").value.toLowerCase()
                document.getElementById("L2").value = letval
                letval = document.getElementById("L3").value.toLowerCase()
                document.getElementById("L3").value = letval
                letval = document.getElementById("L4").value.toLowerCase()
                document.getElementById("L4").value = letval
                letval = document.getElementById("L5").value.toLowerCase()
                document.getElementById("L5").value = letval
                letval = document.getElementById("L6").value.toLowerCase()
                document.getElementById("L6").value = letval
                letval = document.getElementById("L7").value.toLowerCase()
                document.getElementById("L7").value = letval
                letval = document.getElementById("L8").value.toLowerCase()
                document.getElementById("L8").value = letval
                letval = document.getElementById("L9").value.toLowerCase()
                document.getElementById("L9").value = letval

                //SECOND ROW OF LETTERS
                letval = document.getElementById("M0").value.toLowerCase()
                document.getElementById("M0").value = letval
                letval = document.getElementById("M1").value.toLowerCase()
                document.getElementById("M1").value = letval
                letval = document.getElementById("M2").value.toLowerCase()
                document.getElementById("M2").value = letval
                letval = document.getElementById("M3").value.toLowerCase()
                document.getElementById("M3").value = letval
                letval = document.getElementById("M4").value.toLowerCase()
                document.getElementById("M4").value = letval
                letval = document.getElementById("M5").value.toLowerCase()
                document.getElementById("M5").value = letval
                letval = document.getElementById("M6").value.toLowerCase()
                document.getElementById("M6").value = letval
                letval = document.getElementById("M7").value.toLowerCase()
                document.getElementById("M7").value = letval
                letval = document.getElementById("M8").value.toLowerCase()
                document.getElementById("M8").value = letval

                //LAST ROW OF LETTERS
                letval = document.getElementById("N0").value.toLowerCase()
                document.getElementById("N0").value = letval
                letval = document.getElementById("N1").value.toLowerCase()
                document.getElementById("N1").value = letval
                letval = document.getElementById("N2").value.toLowerCase()
                document.getElementById("N2").value = letval
                letval = document.getElementById("N3").value.toLowerCase()
                document.getElementById("N3").value = letval
                letval = document.getElementById("N4").value.toLowerCase()
                document.getElementById("N4").value = letval
                letval = document.getElementById("N5").value.toLowerCase()
                document.getElementById("N5").value = letval
                letval = document.getElementById("N6").value.toLowerCase()
                document.getElementById("N6").value = letval


            }
            else {
                letval = document.getElementById("L0").value.toUpperCase()
                document.getElementById("L0").value = letval
                letval = document.getElementById("L1").value.toUpperCase()
                document.getElementById("L1").value = letval
                letval = document.getElementById("L2").value.toUpperCase()
                document.getElementById("L2").value = letval
                letval = document.getElementById("L3").value.toUpperCase()
                document.getElementById("L3").value = letval
                letval = document.getElementById("L4").value.toUpperCase()
                document.getElementById("L4").value = letval
                letval = document.getElementById("L5").value.toUpperCase()
                document.getElementById("L5").value = letval
                letval = document.getElementById("L6").value.toUpperCase()
                document.getElementById("L6").value = letval
                letval = document.getElementById("L7").value.toUpperCase()
                document.getElementById("L7").value = letval
                letval = document.getElementById("L8").value.toUpperCase()
                document.getElementById("L8").value = letval
                letval = document.getElementById("L9").value.toUpperCase()
                document.getElementById("L9").value = letval

                //SECOND ROW OF LETTERS
                letval = document.getElementById("M0").value.toUpperCase()
                document.getElementById("M0").value = letval
                letval = document.getElementById("M1").value.toUpperCase()
                document.getElementById("M1").value = letval
                letval = document.getElementById("M2").value.toUpperCase()
                document.getElementById("M2").value = letval
                letval = document.getElementById("M3").value.toUpperCase()
                document.getElementById("M3").value = letval
                letval = document.getElementById("M4").value.toUpperCase()
                document.getElementById("M4").value = letval
                letval = document.getElementById("M5").value.toUpperCase()
                document.getElementById("M5").value = letval
                letval = document.getElementById("M6").value.toUpperCase()
                document.getElementById("M6").value = letval
                letval = document.getElementById("M7").value.toUpperCase()
                document.getElementById("M7").value = letval
                letval = document.getElementById("M8").value.toUpperCase()
                document.getElementById("M8").value = letval

                //LAST ROW OF LETTERS
                letval = document.getElementById("N0").value.toUpperCase()
                document.getElementById("N0").value = letval
                letval = document.getElementById("N1").value.toUpperCase()
                document.getElementById("N1").value = letval
                letval = document.getElementById("N2").value.toUpperCase()
                document.getElementById("N2").value = letval
                letval = document.getElementById("N3").value.toUpperCase()
                document.getElementById("N3").value = letval
                letval = document.getElementById("N4").value.toUpperCase()
                document.getElementById("N4").value = letval
                letval = document.getElementById("N5").value.toUpperCase()
                document.getElementById("N5").value = letval
                letval = document.getElementById("N6").value.toUpperCase()
                document.getElementById("N6").value = letval
            }
            return true;
        }

        /*--------------------------------------------
        |                                            |
        |  LOGIC TO GENERATE NUMBERS                 |
        |                                            |
        --------------------------------------------*/

        // Possible Values for NUMBERS
        var array_PosNumbersRow = new Array();
        var array_NumbersRow = new Array();
        array_NumbersRow[0] = '<input class=keys_regular type="button" id="vk0" title="0" onclick=PasswordVK(array_PosNumbersRow[0]) ondblclick=PasswordVK2(array_PosNumbersRow[0]) onkeypress="return EnterButton(event)" value="0" />'
        array_NumbersRow[1] = '<input class=keys_regular type="button" id="vk1" title="1" onclick=PasswordVK(array_PosNumbersRow[1]) ondblclick=PasswordVK2(array_PosNumbersRow[1]) onkeypress="return EnterButton(event)" value="1" />'
        array_NumbersRow[2] = '<input class=keys_regular type="button" id="vk2" title="2" onclick=PasswordVK(array_PosNumbersRow[2]) ondblclick=PasswordVK2(array_PosNumbersRow[2]) onkeypress="return EnterButton(event)" value="2" />'
        array_NumbersRow[3] = '<input class=keys_regular type="button" id="vk3" title="3" onclick=PasswordVK(array_PosNumbersRow[3]) ondblclick=PasswordVK2(array_PosNumbersRow[3]) onkeypress="return EnterButton(event)" value="3" />'
        array_NumbersRow[4] = '<input class=keys_regular type="button" id="vk4" title="4" onclick=PasswordVK(array_PosNumbersRow[4]) ondblclick=PasswordVK2(array_PosNumbersRow[4]) onkeypress="return EnterButton(event)" value="4" />'
        array_NumbersRow[5] = '<input class=keys_regular type="button" id="vk5" title="5" onclick=PasswordVK(array_PosNumbersRow[5]) ondblclick=PasswordVK2(array_PosNumbersRow[5]) onkeypress="return EnterButton(event)" value="5" />'
        array_NumbersRow[6] = '<input class=keys_regular type="button" id="vk6" title="6" onclick=PasswordVK(array_PosNumbersRow[6]) ondblclick=PasswordVK2(array_PosNumbersRow[6]) onkeypress="return EnterButton(event)" value="6" />'
        array_NumbersRow[7] = '<input class=keys_regular type="button" id="vk7" title="7" onclick=PasswordVK(array_PosNumbersRow[7]) ondblclick=PasswordVK2(array_PosNumbersRow[7]) onkeypress="return EnterButton(event)" value="7" />'
        array_NumbersRow[8] = '<input class=keys_regular type="button" id="vk8" title="8" onclick=PasswordVK(array_PosNumbersRow[8]) ondblclick=PasswordVK2(array_PosNumbersRow[8]) onkeypress="return EnterButton(event)" value="8" />'
        array_NumbersRow[9] = '<input class=keys_regular type="button" id="vk9" title="9" onclick=PasswordVK(array_PosNumbersRow[9]) ondblclick=PasswordVK2(array_PosNumbersRow[9]) onkeypress="return EnterButton(event)" value="9" />'


        // Generate NUMBERS
        function NumbersRow() {
            var disporder = "N";
            var index = 0;
            var random = 0; // variable used to contain controlled random number
            // while all of array elements haven't been cycled thru
            while (index < array_NumbersRow.length) {
                // generate random number between 0 and arraylength-1
                random = Math.floor(Math.random() * array_NumbersRow.length);
                // if element hasn't been marked as "selected"
                if (array_NumbersRow[random] != "selected") {
                    if (random == 0 && index == 0) {
                        // do nothing
                        // alert("random=" + random + " index=" + index)
                    }
                    else {
                        if (index == 0) {
                            document.write('<a href="" id="hrefvk" accesskey="1" title="Beginning of Numbers Row"></a>')
                        };

                        document.write(array_NumbersRow[random]);
                        array_NumbersRow[random] = "selected"; // mark element as selected
                        //array_PosNumbersRow[random] ="N"+index;
                        array_PosNumbersRow[random] = index;
                        disporder += random
                        //alert(random + " is at position " + array_PosNumbersRow[random])
                        index++;
                    }
                }
            }
            //alert("disporder=" + disporder)
            return disporder;

        }

        /*--------------------------------------------
        |                                            |
        |  LOGIC TO GENERATE SPECIAL CHARACTERS      |
        |                                            |
        --------------------------------------------*/

        // Possible Values for SPECIAL CHARACTERS
        var array_SpecialCharactersRow = new Array();
        var array_PosSpecial = new Array();
        array_SpecialCharactersRow[0] = '<input class=keys_regular type="button" alt="exclamation-mark" onclick=PasswordVK(array_PosSpecial[0]) ondblclick=PasswordVK2(array_PosSpecial[0]) onkeypress="return EnterButton(event)" value="!" title="exclamation-mark" />'
        array_SpecialCharactersRow[1] = '<input class=keys_regular type="button" id="S1" onclick=PasswordVK(array_PosSpecial[1]) ondblclick=PasswordVK2(array_PosSpecial[1]) onkeypress="return EnterButton(event)" value="&#064;" title="at-sign" />'
        array_SpecialCharactersRow[2] = '<input class=keys_regular type="button" id="S2" onclick=PasswordVK(array_PosSpecial[2]) ondblclick=PasswordVK2(array_PosSpecial[2]) onkeypress="return EnterButton(event)" value="&#035;" title="number-sign" />'
        array_SpecialCharactersRow[3] = '<input class=keys_regular type="button" id="S3" onclick=PasswordVK(array_PosSpecial[3]) ondblclick=PasswordVK2(array_PosSpecial[3]) onkeypress="return EnterButton(event)" value="&#036;" title="dollar-sign" />'
        array_SpecialCharactersRow[4] = '<input class=keys_regular type="button" id="S4" onclick=PasswordVK(array_PosSpecial[4]) ondblclick=PasswordVK2(array_PosSpecial[4]) onkeypress="return EnterButton(event)" value="&#037;" title="percent-sign" />'
        array_SpecialCharactersRow[5] = '<input class=keys_regular type="button" id="S5" onclick=PasswordVK(array_PosSpecial[5]) ondblclick=PasswordVK2(array_PosSpecial[5]) onkeypress="return EnterButton(event)" value="&#043;" title="plus-sign" />'
        array_SpecialCharactersRow[6] = '<input class=keys_regular type="button" id="S6" onclick=PasswordVK(array_PosSpecial[6]) ondblclick=PasswordVK2(array_PosSpecial[6]) onkeypress="return EnterButton(event)" value="&#094;" title="caret" />'
        array_SpecialCharactersRow[7] = '<input class=keys_regular type="button" id="S7" onclick=PasswordVK(array_PosSpecial[7]) ondblclick=PasswordVK2(array_PosSpecial[7]) onkeypress="return EnterButton(event)" value="&#042;" title="asterisk" />'
        array_SpecialCharactersRow[8] = '<input class=keys_regular type="button" id="S8" onclick=PasswordVK(array_PosSpecial[8]) ondblclick=PasswordVK2(array_PosSpecial[8]) onkeypress="return EnterButton(event)" value="&#061;" title="equal-sign" />'
        array_SpecialCharactersRow[9] = '<input class=keys_regular type="button" id="S9" onclick=PasswordVK(array_PosSpecial[9]) ondblclick=PasswordVK2(array_PosSpecial[9]) onkeypress="return EnterButton(event)" value="&#095;" title="underscore" />'


        // Generate SPECIAL CHARACTERS
        function SpecialCharactersRow() {
            var SString = "!@#$%+^*=_";
            var disporder = "S";
            var index = 0;
            var random = 0; // variable used to contain controlled random number
            // while all of array elements haven't been cycled thru
            while (index < array_SpecialCharactersRow.length) {
                // generate random number between 0 and arraylength-1
                random = Math.floor(Math.random() * array_SpecialCharactersRow.length);
                // if element hasn't been marked as "selected"
                if (array_SpecialCharactersRow[random] != "selected") {
                    if (index == 0)
                    { document.write('<a href="" accesskey="2" title="Beginning of Characters Row"></a>') };
                    document.write(array_SpecialCharactersRow[random]);
                    array_SpecialCharactersRow[random] = "selected"; // mark element as selected
                    array_PosSpecial[random] = SString.substring(index, index + 1)
                    disporder = disporder + SString.substring(random, random + 1)
                    index++;
                }
            }
            return disporder;
        }

        /*--------------------------------------------
        |                                            |
        |  LOGIC TO GENERATE LETTERS ON TOP ROW      |
        |                                            |
        --------------------------------------------*/

        // Possible Values for LETTERS ON TOP ROW
        var array_LettersTopRow = new Array();
        var array_PosLettersTopRow = new Array();
        array_LettersTopRow[0] = '<input class=keys_regular type="button" id="L0" title="Quebec" onclick=PasswordVK("Q") ondblclick=PasswordVK2("Q") onkeypress="return EnterButton(event)" value="Q" />'
        array_LettersTopRow[1] = '<input class=keys_regular type="button" id="L1" title="Whiskey" onclick=PasswordVK("W") ondblclick=PasswordVK2("W") onkeypress="return EnterButton(event)" value="W" />'
        array_LettersTopRow[2] = '<input class=keys_regular type="button" id="L2" title="Echo" onclick=PasswordVK("E") ondblclick=PasswordVK2("E") onkeypress="return EnterButton(event)" value="E" />'
        array_LettersTopRow[3] = '<input class=keys_regular type="button" id="L3" title="Romeo" onclick=PasswordVK("R") ondblclick=PasswordVK2("R") onkeypress="return EnterButton(event)" value="R" />'
        array_LettersTopRow[4] = '<input class=keys_regular type="button" id="L4" title="Tango" onclick=PasswordVK("T") ondblclick=PasswordVK2("T") onkeypress="return EnterButton(event)" value="T" />'
        array_LettersTopRow[5] = '<input class=keys_regular type="button" id="L5" title="Yankee" onclick=PasswordVK("Y") ondblclick=PasswordVK2("Y") onkeypress="return EnterButton(event)" value="Y" />'
        array_LettersTopRow[6] = '<input class=keys_regular type="button" id="L6" title="Uniform" onclick=PasswordVK("U") ondblclick=PasswordVK2("U") onkeypress="return EnterButton(event)" value="U" />'
        array_LettersTopRow[7] = '<input class=keys_regular type="button" id="L7" title="India" onclick=PasswordVK("I") ondblclick=PasswordVK2("I") onkeypress="return EnterButton(event)" value="I" />'
        array_LettersTopRow[8] = '<input class=keys_regular type="button" id="L8" title="Oscar" onclick=PasswordVK("O") ondblclick=PasswordVK2("O") onkeypress="return EnterButton(event)" value="O" />'
        array_LettersTopRow[9] = '<input class=keys_regular type="button" id="L9" title="Papa" onclick=PasswordVK("P") ondblclick=PasswordVK2("P") onkeypress="return EnterButton(event)" value="P" />'

        // Generate LETTERS ON TOP ROW
        function LettersTopRow() {
            var AString = "QWERTYUIOP";
            var disporder = "A";
            var index = 0;
            var random = 0; // variable used to contain controlled random number
            // while all of array elements haven't been cycled thru
            while (index < array_LettersTopRow.length) {
                // generate random number between 0 and arraylength-1
                random = Math.floor(Math.random() * array_LettersTopRow.length);
                // if element hasn't been marked as "selected"
                if (array_LettersTopRow[random] != "selected") {
                    if (index == 0)
                    { document.write('<a href="" accesskey="3" title="Row 1 of Letters"></a>') };

                    document.write(array_LettersTopRow[random]);
                    array_LettersTopRow[random] = "selected"; // mark element as selected
                    array_PosLettersTopRow[random] = AString.substring(index, index + 1)
                    disporder = disporder + AString.substring(random, random + 1)
                    index++;
                }
            }
            //alert("disporder=" + disporder)
            return disporder;
        }

        /*--------------------------------------------
        |                                            |
        |  LOGIC TO GENERATE LETTERS ON MIDDLE ROW   |
        |                                            |
        --------------------------------------------*/

        // Possible Values for LETTERS ON MIDDLE ROW
        var array_LettersMiddleRow = new Array();
        var array_PosLettersMiddleRow = new Array();
        array_LettersMiddleRow[0] = '<input class=keys_regular type="button" id="M0" title="Alpha" onclick=PasswordVK("A") ondblclick=PasswordVK2("A") onkeypress="return EnterButton(event)" value="A" />'
        array_LettersMiddleRow[1] = '<input class=keys_regular type="button" id="M1" title="Sierra" onclick=PasswordVK("S") ondblclick=PasswordVK2("S") onkeypress="return EnterButton(event)" value="S" />'
        array_LettersMiddleRow[2] = '<input class=keys_regular type="button" id="M2"title="Delta" onclick=PasswordVK("D") ondblclick=PasswordVK2("D") onkeypress="return EnterButton(event)" value="D" />'
        array_LettersMiddleRow[3] = '<input class=keys_regular type="button" id="M3" title="Foxtrot" onclick=PasswordVK("F") ondblclick=PasswordVK2("F") onkeypress="return EnterButton(event)" value="F" />'
        array_LettersMiddleRow[4] = '<input class=keys_regular type="button" id="M4" title="Golf" onclick=PasswordVK("G") ondblclick=PasswordVK2("G") onkeypress="return EnterButton(event)" value="G" />'
        array_LettersMiddleRow[5] = '<input class=keys_regular type="button" id="M5" title="Hotel" onclick=PasswordVK("H") ondblclick=PasswordVK2("H") onkeypress="return EnterButton(event)" value="H" />'
        array_LettersMiddleRow[6] = '<input class=keys_regular type="button" id="M6" title="Juliet" onclick=PasswordVK("J") ondblclick=PasswordVK2("J") onkeypress="return EnterButton(event)" value="J" />'
        array_LettersMiddleRow[7] = '<input class=keys_regular type="button" id="M7" title="Kilo" onclick=PasswordVK("K") ondblclick=PasswordVK2("K") onkeypress="return EnterButton(event)" value="K" />'
        array_LettersMiddleRow[8] = '<input class=keys_regular type="button" id="M8" title="Lima" onclick=PasswordVK("L") ondblclick=PasswordVK2("L") onkeypress="return EnterButton(event)" value="L" />'


        // Generate LETTERS ON MIDDLE ROW
        function LettersMiddleRow() {
            var disporder = "B";
            var BString = "ASDFGHJKL"
            var index = 0;
            var random = 0; // variable used to contain controlled random number
            // while all of array elements haven't been cycled thru
            while (index < array_LettersMiddleRow.length) {
                // generate random number between 0 and arraylength-1
                random = Math.floor(Math.random() * array_LettersMiddleRow.length);
                // if element hasn't been marked as "selected"
                if (array_LettersMiddleRow[random] != "selected") {
                    if (index == 0)
                    { document.write('<a href="" accesskey="4" title="Row 2 of Letters"></a>') };

                    document.write(array_LettersMiddleRow[random]);
                    array_LettersMiddleRow[random] = "selected"; // mark element as selected
                    array_PosLettersMiddleRow[random] = BString.substring(index, index + 1)
                    disporder = disporder + BString.substring(random, random + 1)
                    index++;
                }
            }
            //alert("disporder=" + disporder)
            return disporder;
        }

        /*--------------------------------------------
        |                                            |
        |  LOGIC TO GENERATE LETTERS ON BOTTOM ROW   |
        |                                            |
        --------------------------------------------*/

        // Possible Values for LETTERS ON BOTTOM ROW
        var array_LettersBottomRow = new Array();
        var array_PosLettersBottomRow = new Array();
        array_LettersBottomRow[0] = '<input class=keys_regular id="N0" type="button" title="Zulu" onclick=PasswordVK("Z") ondblclick=PasswordVK2("Z") onkeypress="return EnterButton(event)" value="Z" />'
        array_LettersBottomRow[1] = '<input class=keys_regular id="N1" type="button" title="X-ray" onclick=PasswordVK("X") ondblclick=PasswordVK2("X") onkeypress="return EnterButton(event)" value="X" />'
        array_LettersBottomRow[2] = '<input class=keys_regular id="N2" type="button" title="Charlie" onclick=PasswordVK("C") ondblclick=PasswordVK2("C") onkeypress="return EnterButton(event)" value="C" />'
        array_LettersBottomRow[3] = '<input class=keys_regular id="N3" type="button" title="Victor" onclick=PasswordVK("V") ondblclick=PasswordVK2("V") onkeypress="return EnterButton(event)" value="V" />'
        array_LettersBottomRow[4] = '<input class=keys_regular id="N4" type="button" title="Bravo" onclick=PasswordVK("B") ondblclick=PasswordVK2("B") onkeypress="return EnterButton(event)" value="B" />'
        array_LettersBottomRow[5] = '<input class=keys_regular id="N5" type="button" title="November" onclick=PasswordVK("N") ondblclick=PasswordVK2("N") onkeypress="return EnterButton(event)" value="N" />'
        array_LettersBottomRow[6] = '<input class=keys_regular id="N6" type="button" title="Mike" onclick=PasswordVK("M") ondblclick=PasswordVK2("M") onkeypress="return EnterButton(event)" value="M" />'

        // Generate LETTERS ON BOTTOM ROW
        function LettersBottomRow() {
            var disporder = "C";
            var CString = "ZXCVBNM"
            var index = 0;
            var random = 0; // variable used to contain controlled random number
            // while all of array elements haven't been cycled thru
            while (index < array_LettersBottomRow.length) {
                // generate random number between 0 and arraylength-1
                random = Math.floor(Math.random() * array_LettersBottomRow.length);
                // if element hasn't been marked as "selected"
                if (array_LettersBottomRow[random] != "selected") {
                    if (index == 0)
                    { document.write('<a href="" accesskey="5" title="Row 3 of Letters"></a>') };

                    document.write(array_LettersBottomRow[random]);
                    array_LettersBottomRow[random] = "selected"; // mark element as selected
                    array_PosLettersBottomRow[random] = CString.substring(index, index + 1)
                    disporder = disporder + CString.substring(random, random + 1)
                    index++;
                }
            }
            return disporder;
        }

        /*--------------------------------------------
        |                                            |
        |  LOGIC TO CHECK IF ENTER HAS BEEN PRESSED  |
        |                                            |
        --------------------------------------------*/
        function EnterButton(event) {
            if (debug_mode == true) {
                alert("Key = " + event.keyCode);
            }
            if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)) {
                var answer = confirm("Do you want to submit the form?");
                // Yes
                if (answer) {
                    if (event.which) {
                        document.Form1.btnSubmit.click();
                        return false;
                    }
                    document.Form1.btnSubmit.click();
                    return false;
                }

                // No
                // FireFox
                if (event.which) {
                    return false;
                }
                // IE
                event.keyCode = 0;
                event.returnvalue = false;
                return false;
            }
        }

    </script>

<style type="text/css">
    .style1
    {
    background-color: White;
    border-style: none;
    font-size: 83%;
    font-family: Verdana;
    font-weight: normal;
    color: Black;
    text-align: left;
    width: 485px;
    }
</style>
</head>
<body onkeydown="onKeyDown();" onload="setFocus();">
<form name="Form1" method="post" action="LoginReEntry.aspx?AccessString=ABORT&amp;globalID=DNET000320150110075739W1662384&amp;GID=fa1d40a8-536e-4d95-bac3-f3a22bc26f44&amp;dttm=01102015075739" id="Form1" autocomplete="off" onsubmit="verifyIt()">
<div>
    <input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="" />
    <input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="" />
    <input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUKMTg4NzU2OTUzNw9kFgICAw9kFgQCCQ9kFgoCAQ8PFgIeBFRleHQFQVBWLTE8YnI+PGJyPkFuIGVycm9yIG9jY3VycmVkIHdpdGggeW91ciBsb2dpbi4gIFBsZWFzZSB0cnkgYWdhaW4uZGQCCw8PFgIeCFJlYWRPbmx5aGRkAg0PD2QWAh4Hb25jbGljawU9amF2YXNjcmlwdDppZih2ZXJpZnlDbGljaygpKXtyZXR1cm4gdHJ1ZTt9ZWxzZXtyZXR1cm4gZmFsc2U7fWQCDw8WAh4HVmlzaWJsZWhkAhEPFgQeBXZhbHVlBUxUbyBlbnRlciB5b3VyIFBhc3N3b3JkIG1vcmUgc2VjdXJlbHksIGNsaWNrIGhlcmUgZm9yIHRoZSBPbi1TY3JlZW4gS2V5Ym9hcmQuHwIFPGphdmFzY3JpcHQ6aWYodmVyaWZ5TGluaygpKXtyZXR1cm4gdHJ1ZTt9ZWxzZXtyZXR1cm4gZmFsc2U7fWQCCw9kFggCAQ8PZBYCHwIFPmphdmFzY3JpcHQ6aWYodmVyaWZ5U3VibWl0KCkpe3JldHVybiB0cnVlO31lbHNle3JldHVybiBmYWxzZTt9ZAIDDxYCHwIFPGphdmFzY3JpcHQ6aWYoc2V0Q2xpY2tpdCgpKXtyZXR1cm4gdHJ1ZTt9ZWxzZXtyZXR1cm4gZmFsc2U7fWQCBQ8WAh8CBTxqYXZhc2NyaXB0OmlmKHNldENsaWNraXQoKSl7cmV0dXJuIHRydWU7fWVsc2V7cmV0dXJuIGZhbHNlO31kAgcPFgIfAgU8amF2YXNjcmlwdDppZihzZXRDbGlja2l0KCkpe3JldHVybiB0cnVlO31lbHNle3JldHVybiBmYWxzZTt9ZGQGpra2TcHAgbqiiWO722putxfBYw==" />
</div>

<script type="text/javascript">
//<![CDATA[
var theForm = document.forms['Form1'];
if (!theForm) {
    theForm = document.Form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}
//]]>
</script>


<div>

    <input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="6E4037A7" />
    <input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEWDgKw1IdaAqDugIUBApbEoqwJAre1xZkIAobzseQGArn7losEAuzRsusGAsazqMMMArXb+akGArau188NAsKL2t4DAs6Wi4YGAv3xiIkGAv/f0ZQPKIeAVUGzsjtZrJbydpbPGCcQ0NA=" />
</div>
<input name="visOrder" type="hidden" id="visOrder" />
<input id="visFocus" type="hidden" name="visFocus" />
<input name="visSmartEmail" type="hidden" id="visSmartEmail" />
<input name="hidEnter" type="hidden" id="hidEnter" />
<table id="outerTable" cellspacing="0" cellpadding="0" width="730" align="center"
       border="0">
    <tr>
        <td>
                <span id="MyPayHeader"><span id="clientEventHandlersJS"><script id="clientEventHandlersJS" language="javascript">
    winname=window.name;

   //No RIGHT CLICK
   if (window.Event) {
     //document.captureEvents(Event.MOUSEUP | Event.MOUSEDOWN | Event.KEYPRESS);
  document.oncontextmenu = nocontextmenu;
  document.onMouseDown = norightclick;
  document.onMouseUp = norightclick;
   }

   function nocontextmenu()	{
       if (window.Event)	{
           Event.cancelBubble = true ;
           Event.returnValue = false;
       }
       return false;
   }

   function norightclick(e) {
       var br = new String(navigator.appName+navigator.appVersion);
       // Netscape first
       if (window.Event) {
           if (e.which != 1) {
	            return false;
           }
       }
       else
            if (Event.button !=1) {
	            // IE 4 requires a replacement(?)
	            if (br.indexOf('MSIE 4')>-1 )
		            alert("Function disabled");
	            Event.cancelBubble = true ;
	            Event.returnValue = false;
	            return false;
           }

   }
  document.oncontextmenu = nocontextmenu;
  document.onMouseDown = norightclick;
  document.onMouseUp = norightclick;

  // ****************************
  // Block backspace onKeyDown
  // ***************************
  function onKeyDown() {
 if (typeof window.event != 'undefined') // IE
   document.onkeydown = function() // IE
     {
     var t=event.srcElement.type;
     var kc=event.keyCode;
     return ((kc != 8) || ( t == 'text' &&  kc != 13) || ( t == 'password' &&  kc != 13 && !event.srcElement.readOnly ) ||
              (t == 'textarea') || ( t == 'submit' &&  kc == 13))
     }
 else
   document.onkeypress = function(e)  // FireFox/Others
     {
     var t=e.target.type;
     var kc=e.keyCode;
     if ((kc != 8) || ( t == 'text' &&  kc != 13) || ( t == 'password' &&  kc != 13) ||
         (t == 'textarea') || ( t == 'submit' &&  kc == 13))
         return true
     else {
         return false
     }
    }
       if (event.altKey && (event.keyCode == 49 || event.keyCode == 50 || event.keyCode == 51 || event.keyCode == 52 || event.keyCode == 53 || event.keyCode == 54 )){
         return true;}
       if (
       (event.altKey)
           ||	( (event.keyCode == 8) && (event.srcElement.type != "text" && event.srcElement.type != "textarea" && event.srcElement.type != "password") )
           ||	( (event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) )
           ||	(event.keyCode == 116)
           ||	( (event.srcElement.type == "text") && ((event.srcElement.disabled == true) || (event.srcElement.readOnly == true)) )
        ) {
	            // Only allow ALT-TAB(system navigation) and disallow
	            // the ALT+RIGHTARROW(37), ALT+LEFTARROW(39) keys, or ALT+HOME(36).
                if (event.altKey && (event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 36))
                  {alert('The ALT navigation keys are disabled');
	                   event.keyCode = 0;
	                   event.returnValue = false;}
        }
   }

  var nm = navigator.userAgent+navigator.appVersion;
   if((winname=="mpeex") || (nm.indexOf('MSNTV')>-1))
   { }
   else
   {
     document.location.href='Logout.htm';
   }

   function timeout2()
   {
         document.location.href='Logout.htm';
   }

   function timeout()
   {
   //alert("Inside Timeout")
   setTimeout("window.close()",600000)
        if (confirm('Click OK for more time.'))
        {
            setTimeout("timeout2()",600000);
        }
        else
        {
            document.location.href='Logout.htm';
        }
    }


    function document_onclick() {
    }

    setTimeout("timeout()",1300000);
         //-->
   </script>
   <script language="javascript" for="document" event="onclick">
         return document_onclick();

   </script>
   <noscript>
       Javascript is required for myPay but is used primarily for client side validation.
   </noscript>
</span><table id="header" width="730" border="0" cellspacing="0" cellpadding="0" name="header" align="center">
                    <tr>
                        <td bgcolor="#1e2f81" scope="row" colspan="4"><img src="image/indexhdr1b.jpg" border="0" height="80" alt="myPay" width="730" /></td>
                    </tr>
                    <tr>
                        <td onmouseout="window.status='';return true" align="left" bgcolor="#595959" width="500" onmouseover="window.status='DI-05';return true" onblur="window.status='';return true" style="font-family:Verdana, Arial, Helvetica, sans-serif;color: White;font-size:13pt;text-decoration:	none;" onfocus="window.status='DI-05';return true" tabindex="3" scope="col">&nbsp;&nbsp;&nbsp;&nbsp;Security</td>
                        <td valign="top" align="right" width="200" bgcolor="#595959" height="24" scope="col">&nbsp;</td>
                        <td valign="top" align="right" width="45" bgcolor="#595959" height="24" scope="col"><a href="javascript: window.close()" style="font-family:Arial, Helvetica, sans-serif;	color: White;font-size:8pt;	font-weight:bold;text-decoration:none;vertical-align:top;text-align:right;width:35;" tabindex="2"><img src="image/mnuspacer.gif" alt="" width="3" border="0" /><span>&nbsp;&nbsp;Exit&nbsp;</span></a></td>
                    </tr>
                </table>
</span>
        </td>
    </tr>
</table>
<div id="pnlEntry">

    <table cellspacing="0" cellpadding="0" width="730" align="center" border="0">
        <tr>
            <td class="InfoMessage" style="width: 772px">
                <p class="centered">
                    <span id="lblMsg" style="display:inline-block;color:#E00000;font-weight:bold;width:720px;">PV-1<br><br>An error occurred with your login.  Please try again.</span>
                    <br />
                    <strong>Enter your Login ID and Password below. </strong>
                </p>
                <table id="DataEntry" cellspacing="2" cellpadding="2" width="75%" align="center"
                       summary="Restricted Access Pin Change Table" border="0">
                    <tr class="OddRow">
                        <th class="FieldTitle" id="r1" scope="row">
                            <label for="txtLogin">
                                Enter Login ID:</label>&nbsp;
                        </th>
                        <td class="Field" headers="r1 c2">
                            <input name="txtLogin" type="text" maxlength="129" size="40" id="txtLogin" tabindex="4" />
                        </td>
                    </tr>
                    <!--
                    <tr class="EvenRow">
                        <th class="FieldTitle" id="r2" scope="row">
                            <span id="Label1" style="color:#E00000;font-size:Small;font-weight:bold;">OR </span>
                            <label for="txtSSN">
                                Enter SSN:</label>&nbsp;
                        </th>
                        <td class="Field" headers="r2 c2">
                            <input name="txtSSN" type="password" maxlength="9" size="10" id="txtSSN" />
                            <font color="#E00000">ONLY if you have not established a Login ID.</font>
                        </td>
                    </tr>
                    -->
                    <tr>
                        <td colspan="2" class="InfoMessage">
                            <input name="TextBox1" type="password" id="TextBox1" tabindex="99" style="color:White;border-color:White;border-style:None;width:16px;" />
                            <br />
                            <p class="centered">
                                <strong>&nbsp;</strong>
                            </p>
                        </td>
                    </tr>
                    <tr class="OddRow">
                        <th class="FieldTitle" id="r3" scope="row">
                            <label for="newPin">
                                Enter Password:</label>&nbsp;
                        </th>
                        <td class="Field" headers="r3 c2">
                            <input name="newPin" type="password" maxlength="30" size="40" id="newPin" tabindex="5" />
                            <input type="submit" name="btnHidden" value="" onclick="javascript:if(verifyClick()){return true;}else{return false;};" id="btnHidden" tabindex="98" style="background-color:#DDDDFF;border-color:#DDDDFF;border-style:None;" />
                        </td>
                    </tr>

                </table>
                <div class="centered">
                    <input name="btnLink" type="submit" id="btnLink" value="To enter your Password more securely, click here for the On-Screen Keyboard." style="border-style: none; border-color: inherit; border-width: 0; background-color: #FFFFFF; color: #0000FF; text-decoration: underline; font-weight: bold; height: 22px;" tabindex="7" onclick="javascript:if(verifyLink()){return true;}else{return false;}" />
                </div>
            </td>
        </tr>
    </table>

</div>
<div id="pnlButtons">

    <p class="centered">
        <input type="submit" name="btnSubmit" value="Accept / Submit" onclick="javascript:if(verifySubmit()){return true;}else{return false;};" id="btnSubmit" accesskey="6" tabindex="6" /></p>
    <hr />
    <table cellspacing="0" cellpadding="0" align="center" border="0" width="730">
        <tr>
            <td width="15%" class="PurpleText" align="right" style="font-size: 115%;">
                <b>-----></b>
            </td>
            <td class="style1">
                <div class="centered">
                    <input name="btnForgot" type="submit" id="btnForgot" value="Click here if you have forgotten your Login ID." style="border-style: none; border-color: inherit; border-width: 0; color: #0000FF;
                            text-decoration: underline; font-weight: bold; height: 22px; background-color: #FFFFFF;
                            width: 440px;" tabindex="8" onclick="javascript:if(setClickit()){return true;}else{return false;}" />
                </div>
            </td>
            <td width="15%">
                &nbsp;
            </td>
        </tr>
        <tr>
            <td width="15%" class="PurpleText" align="right" style="font-size: 115%;">
                <b>-----></b>
            </td>
            <td class="style1">
                <div class="centered">
                    <input name="btnPassword" type="submit" id="btnPassword" value="Click here if you have forgotten your Password." style="border-style: none; border-color: inherit; border-width: 0; color: #0000FF;
                            text-decoration: underline; font-weight: bold; height: 22px; background-color: #FFFFFF;" tabindex="9" onclick="javascript:if(setClickit()){return true;}else{return false;}" />
                </div>
            </td>
            <td width="15%">
                &nbsp;
            </td>
        </tr>
        <tr>
            <td width="15%" class="PurpleText" align="right" style="font-size: 115%;">
                <b>-----></b>
            </td>
            <td class="style1">
                <div class="centered">
                    <input name="btnEnroll" type="submit" id="btnEnroll" value="Click here if you have not created a Login ID for account access." style="border-style: none; border-color: inherit; border-width: 0; color: #0000FF;
                            text-decoration: underline; font-weight: bold; height: 22px; background-color: #FFFFFF;
                            width: 440px;" tabindex="10" onclick="javascript:if(setClickit()){return true;}else{return false;}" />
                </div>
            </td>
            <td width="15%">
                &nbsp;
            </td>
        </tr>
    </table>

</div>
</form>
</body>
</html>
