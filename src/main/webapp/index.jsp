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
            <div id="form-wrapper">  
                <form class="form-horizontal" id="myform">
                    
                    <div class="control-group">
                        <label class="control-label" for="inputWorkshopType">Mødetype</label>
                        <div class="controls">
                            <!-- <input type="text" class="input-medium" id="inputWorkshopType" placeholder="Mødetype" data-provide="typeahead" autocomplete="off">-->
                            <select id="inputWorkshopType" class="input-medium">
                                <option>Retrospective</option>
                                <option>Fagmøde</option>
                                <!-- <option>Projektafslutning</option> -->
                            </select>
                        </div>
                    </div>
                    
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
                        <label class="control-label" for="inputNumParticpants">Antal deltagere</label>
                        <div class="controls">
                            <input type="text" id="inputNumParticpants" class="input-medium" placeholder="Antal deltagere">
                        </div>
                    </div>              
                </form>
            </div>
            
            <button id="do-feedback" class="btn btn-small btn-primary">Give feedback</button> 
            <input type="text" id="feedback-value" class="input-medium" placeholder="Grade me!">
                

            <!--Container for method search results-->
            <div id="method-search-result"></div>
            <!--Container for showing the currently selected method -->
            <div id="method-selection"></div>
            <div id="recent-evaluation"></div>
            <div id="evaluation-count"></div>
            <div id="test-output"></div>
            <div id="evaluation-count-hourly"></div>
                   
            
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
                //Cache previous values
                var numCastFeedback = 0;
                var average = 0;
                var tenMostRecent;
                
                setInterval(function() {
                    $.ajax({ url: 'http://10.10.1.99:8080/Workshop/service/feedback/average', 
                        async: true,
                        
                        method: 'get',
                        dataType: 'html',
                        success: function(data) {
                            if(data !== average) {
                                $('#test-output').html('<p>The average score is: '+'<span style="font-weight:700">'+data+'</span></p>');
                                average = data;
                                
                            }
                        }
                        
                    });
                    $.ajax({ url: 'http://10.10.1.99:8080/Workshop/service/feedback/count', 
                        async: true,
                        
                        method: 'get',
                        dataType: 'html',
                        success: function(data) {
                            if(data !== numCastFeedback) {
                                $('#evaluation-count').html('<p>Number of cast feeback: '+'<span style="font-weight:700">'+data+'</span></p>');
                                numCastFeedback = data;
                                
                            } 
                        }
                        
                    });
                    $.ajax({ 
                        url: 'http://10.10.1.99:8080/Workshop/service/feedback/since/'+new Date().getTime(), 
                        async: true,
                        data: {limit: 10},
                        method: 'get',
                        dataType: 'json',
                        success: function(data) {
                            var stringified = JSON.stringify(data);
                            if(stringified !== tenMostRecent) {                                
                                $('#evaluation-count-hourly').html('<p>10 most recent feebacks within the hour:<span style="font-weight:700"> '+data.length+ '</span></p>'+stringified);
                                tenMostRecent = stringified;
                            }
                        }
                        
                    });
                    
                    
                }, 1000);
                
                $("#do-feedback").on('click', function (event) {
                    $.ajax({
                        async: true,
                        type: 'post',
                        url: 'http://10.10.1.99:8080/Workshop/service/feedback/create/'+$('#feedback-value').val()
                    });
                    
                    
                    $('#recent-evaluation').html('<p>You recent evaluation: <span style="font-weight:700"> '+$('#feedback-value').val() +'</span></p>');
                    $('#feedback-value').val(null);
                });
                
                
                $(['#inputNumParticpants','#inputWorkshopType','#inputWorkshopFormat']).each(function(index, value){

                    $(value).on('keyup change',function() {
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