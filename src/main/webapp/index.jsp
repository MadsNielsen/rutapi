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
                            <!-- <input type="text" class="input-small" id="inputWorkshopFormat" placeholder="Mødeform" data-provide="typeahead" autocomplete="off"> -->
                            <select id="inputWorkshopFormat" class="input-medium">
                                <option>Iterationsmøde</option>
                                <option>Milepælsafslutning</option>
                                <option>Projektafslutning</option>
                            </select>                  
                        </div>
                    </div>                
                    <div class="control-group">
                        <label class="control-label" for="inputWorkshopType">Mødetype</label>
                        <div class="controls">
                            <input type="text" class="input-medium" id="inputWorkshopType" placeholder="Mødetype" data-provide="typeahead" autocomplete="off">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="inputNumParticpants">Antal deltagere</label>
                        <div class="controls">
                            <input type="text" id="inputNumParticpants" class="input-medium" placeholder="Antal deltagere">
                        </div>
                    </div>
                </form>
            </div>

            <!--Container for method search results-->
            <div id="method-search-result"></div>
            <!--Container for showing the currently selected method -->
            <div id="method-selection"></div>
        </div>


        <!-- Scripts -->

        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/jquery.fancybox.pack.js"></script>


        <script type="text/javascript">
  
            function doLoadAsyncContent(urlToLoad, targetElement) {
                $.ajax({
                    async: true,
                    type: 'GET',
                    url: urlToLoad,
                    contentType: 'text/html',
                    dataType: 'html',
                    success: function(data) {
                        $(targetElement).html(data);
                    }
                });
            }
            
            $(document).ready(function () {
                $(['#inputNumParticpants','#inputWorkshopType','#inputWorkshopFormat']).each(function(index, value){

                    $(value).on('keyup',function() {
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
                                $("#method-search-result").html('');

                                $.each(data, function(it, data) {
                                    var newAhref = document.createElement("a");
                                    newAhref.setAttribute("href", "http://10.10.1.99:8080/Workshop/service/methods/"+data.id);
                                    newAhref.setAttribute("class", "li-method btn btn-small btn-primary btn-block method-link"+"-"+data.id);                                                               
                                    newAhref.innerHTML = data.title;
                                    $('#method-search-result').append(newAhref);

                                    $("a.method-link-"+data.id).click(function(event) {
                                        event.preventDefault();                                        
                                        doLoadAsyncContent(newAhref.href, '#method-selection');
                                    });
                                })                           
                            },
                            error: function(error) {
                                $(".method-result").html('No results found');		
                            }
                        
                        });
                     
                    });
                });
                
                $("#inputWorkshopType").typeahead({
                    minLength: 0,
                    source: function(query, process) {
                        $.post('/Workshop/service/meetings/types', function(data) {
                            process(data);
                        });
                    }
                });
  
                
                /*Working fancybox
                    $('.method-link').fancybox({
                        'type':'iframe',
                        'loop': true,
                        'margin': 15,
                        'padding': 5,
                        'autoscale':true
                    });
                 */
            });
        </script>
    </body>

</html>