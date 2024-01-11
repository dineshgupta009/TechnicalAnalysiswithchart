 function SelectRedirect(){
        // ON selection of section this function will work
        //alert( document.getElementById('s1').value);

        switch(document.getElementById('s1').value)
        {
        case "NIFTY":
        window.location="http://localhost:1255/NiftyPage";
        break;

        case "BANKNIFTY":
        window.location="http://localhost:1255/FinNiftyPage";
        break;

        case "FINNIFTY":
        window.location="http://localhost:1255/chartStrikePrice";
          break;

        /// Can be extended to other different selections of SubCategory //////
        default:
        window.location="http://localhost:1255/index"; // if no selection matches then redirected to home page
        break;
}// end of switch
}
