<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <title>Workshop App</title>

        <link rel="stylesheet" href="css/core.css" media="screen"/>
        <link rel="stylesheet" href="css/bootstrap.css" media="screen"/>
        <link rel="stylesheet" href="css/handheld.css" media="handheld, only screen and (max-device-width:480px)"/>
        <link rel="stylesheet" href="css/jquery.fancybox.css?v=2.1.4" type="text/css" media="screen" />

    </head>

    <body>
    <div id="wrapper">
        <div id="form-wrapper" style="width:100%">  
            <form class="form-horizontal" id="myform">
              <div class="control-group">
                <label class="control-label" for="inputWorkshopType">Mødeform</label>
                <div class="controls">
                  <input type="text" class="input-small" id="inputWorkshopFormat" placeholder="Mødeform" data-provide="typeahead" autocomplete="off">
                </div>
              </div>                
              <div class="control-group">
                <label class="control-label" for="inputWorkshopType">Mødetype</label>
                <div class="controls">
                  <input type="text" class="input-small" id="inputWorkshopType" placeholder="Mødetype" data-provide="typeahead" autocomplete="off">
                </div>
              </div>
            <div class="control-group">
                <label class="control-label" for="inputNumParticpants">Antal deltagere</label>
                <div class="controls">
                  <input type="text" id="inputNumParticpants" class="input-small" placeholder="Antal deltagere">
                </div>
            </div>
            </form>
        </div>
    </div>
    <!--Hiddent element for the selected method -->
    <div class="method-result"></div>

    <!-- Scripts -->
    
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/jquery.fancybox.pack.js"></script>
    

    <script type="text/javascript">  
            $(document).ready(function () {
                $('#inputNumParticpants').keyup(function() {
                //$('#myform').keyup(function() {                
                     var number = $('#inputNumParticpants').val();
                     var meetingType = $('#inputWorkshopType').val();
                     var dataArguments = {num: number, type: meetingType};
                     
                     $.ajax({
                        async: true,
                        type: "POST",
                        url: "http://10.10.1.99:8080/Workshop/service/methods/find",
                        data: dataArguments,
                        contentType: "application/json",
                        dataType: "json",
                        success: function(data) {
                            
                            if(data.length != 0) {
                                $(".method-result").html('');

                                $.each(data, function(it, data) {
                                    var newAhref = document.createElement("a");
                                    newAhref.setAttribute("href", "http://10.10.1.99:8080/Workshop/service/methods/"+data.id);
                                    newAhref.setAttribute("class", "li-method btn btn-small btn-primary btn-block method-link");                                                               
                                    newAhref.innerHTML = data.title;

                                    $('.method-result').append(newAhref);
                                })
                            }
                        },
                        error: function(error) {
                            $(".method-result").html('No results found');		
                        }
                        
                     });
                });
                
                /*
                * Function to create lookahead for possible meeting types
                */
                
                $("#inputWorkshopType").typeahead({
                    minLength: 0,
                    source: function(query, process) {
                        $.post('/Workshop/service/meetings/types', function(data) {
                            process(data);
                        });
                    }
                });
 
                $('.method-link').fancybox({
                    'type':'iframe',
                    'width': 600, //or whatever you want
                    'height': 300
                });
            });
    </script>
    </body>

</html>