$(document).ready(function() {
    // Transition effect for navbar
    $(window).scroll(function() {
        // checks if window is scrolled more than 100px, adds/removes solid class
        if($(this).scrollTop() > 100)  {
            $('.back_to_top').addClass('show');
            $('.back_to_top').removeClass('hide');
        }

        else {
            $('.back_to_top').addClass('hide');
            $('.back_to_top').removeClass('show');
        }
    });

    $(document).ready(function() {
        $("#back_to_top").click(function(event) {
            event.preventDefault();
            $("html, body").animate({ scrollTop: 0 }, "slow");
        });

    });
});