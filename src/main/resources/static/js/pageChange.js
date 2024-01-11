 function SelectRedirect(){
        // ON selection of section this function will work
        //alert( document.getElementById('s1').value);

        switch(document.getElementById('s1').value)
        {
        case "NIFTY":
        window.location="http://localhost:1255/NiftyPage";
        break;

        case "BANKNIFTY":
        window.location="../asp-tutorial/site_map.php";
        break;

        case "FINNIFTY":
        window.location="http://localhost:1255/chartStrikePrice";
          break;
          case "JS":
            window.location="site_map.php";
        break;



        /// Can be extended to other different selections of SubCategory //////
        default:
        window.location="http://localhost:1255/symbol"; // if no selection matches then redirected to home page
        break;
}// end of switch
}
