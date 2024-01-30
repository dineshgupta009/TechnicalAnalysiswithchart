function msg(){
 alert("Hello Javatpoint");
}

 function SelectRedirect(){
        // ON selection of section this function will work
        //alert( document.getElementById('s1').value);

        switch(document.getElementById('s1').value)
        {
        case "NIFTY":
        window.location="http://localhost:1255/NiftyPage";
        break;

        case "BANKNIFTY":
        window.location="http://localhost:1255/BankNiftyPage";
        break;

        case "FINNIFTY":
        window.location="http://localhost:1255/FinNiftyPage";
          break;

         case "MIDCPNIFTY":
        window.location="http://localhost:1255/MidCpNiftyPage";
          break;
        /// Can be extended to other different selections of SubCategory //////
        default:
        window.location="http://localhost:1255/index"; // if no selection matches then redirected to home page
        break;
}// end of switch
}

function StocksRedirect(){
        // ON selection of section this function will work
        //alert( document.getElementById('s1').value);

        switch(document.getElementById('s2').value)
        {
        case "AARTIIND":
        window.location="http://localhost:1255/AartiIndPage";
        break;

        case "ABB":
        window.location="http://localhost:1255/BankNiftyPage";
        break;

        case "ABBOTINDIA":
        window.location="http://localhost:1255/FinNiftyPage";
          break;

         case "ABCAPITAL":
        window.location="http://localhost:1255/MidCpNiftyPage";
          break;

        case "ABCAPITAL":
        window.location="http://localhost:1255/MidCpNiftyPage";
                    break;
        case "ABCAPITAL":
        window.location="http://localhost:1255/MidCpNiftyPage";
                break;
        case "ABCAPITAL":
        window.location="http://localhost:1255/MidCpNiftyPage";
                 break;
        case "ABCAPITAL":
        window.location="http://localhost:1255/MidCpNiftyPage";
                 break;
        case "COALINDIA":
        window.location="http://localhost:1255/CoalIndiaPage";
                  break;


        /// Can be extended to other different selections of SubCategory //////
        default:
        window.location="http://localhost:1255/index"; // if no selection matches then redirected to home page
        break;
}// end of switch
}