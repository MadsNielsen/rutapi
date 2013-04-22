<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <title>Workshop App</title>

        <link rel="stylesheet" href="css/core.css" media="screen"/>
        <link rel="stylesheet" href="css/bootstrap.css" media="screen"/>
        <link rel="stylesheet" href="css/handheld.css" media="handheld, only screen and (max-device-width:480px)"/>
    </head>

    <body>
    <div id="wrapper">
        <!--
        <div class="navbar">  
            <div class="navbar-inner">  
                <div class="container">  
                    <ul class="nav">  
                        <li><a href="#">Faciliteter</a></li>  
                        <li class="dropdown" id="main-menu">  
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Forbered</a>  
                            <ul class="dropdown-menu">  
                                <li><a href="#">Vælg mødetype</a></li>  
                                <li><a href="#">Vælg antal deltagere</a></li>							
                            </ul>  
                        </li>  
                    </ul>  
                </div>
            </div>  
        </div>
        -->
        <div id="form-wrapper" style="width:100%">  
            <form class="form-horizontal">
              <div class="control-group">
                <label class="control-label" for="inputNumParticpants">Antal deltagere</label>
                <div class="controls">
                  <input type="text" id="inputNumParticpants" class="input-small" placeholder="Antal deltagere">
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="inputWorkshopType">Mødetype</label>
                <div class="controls">
                  <input type="text" class="input-small" id="inputWorkshopType" placeholder="Mødetype">
                </div>
              </div>
                <!--
              <div class="control-group">
                <label class="control-label" for="findSolution">Metode</label>
                <div class="controls">
                  <button type="submit" id="findSolution" class="btn btn-primary">Find metode</button>
                </div>
              </div>
                -->
            </form>
        </div>

        <div class="method-result">
        </div>

    </div>

    <!-- Scripts -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>

     <script type="text/javascript">  
            $(document).ready(function () {  
                $('.dropdown-toggle').dropdown();
                
                $('#inputNumParticpants').keyup(function() {
                     var number = $('#inputNumParticpants').val();
                     var dataArguments = {num: number};
                     
                     $.ajax({
                        async: true,
                        type: "POST",
                        url: "http://10.10.1.99:8080/Workshop/service/methods/find",
                        data: dataArguments,
                        contentType: "application/json",
                        dataType: "json",
                        success: function(data) {
                            $(".method-result").html('');
                            
                            $.each(data, function(it, data) {
                                var newAhref = document.createElement("a");
                                newAhref.setAttribute("href", "http://10.10.1.99:8080/Workshop/service/methods/"+data.id);
                                newAhref.setAttribute("class", "li-method btn btn-large btn-primary btn-block")
                                newAhref.innerHTML = data.metodologyText;
                   
                                $('.method-result').append(newAhref);
                            })                        
                        },
                        error: function(error) {
                            $(".method-result").html('No results found');		
                        }
                        
                     });
                });
                
            });

    </script>
    
    <script type="text/javascript">
        
    </script>
    
    

    </body>

</html>