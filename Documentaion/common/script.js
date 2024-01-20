
function copyClip(copyText){
    var aux = document.createElement("input");
    aux.setAttribute("value", copyText);
    document.body.appendChild(aux);
    aux.select();
    document.execCommand("copy");
    document.body.removeChild(aux);
}

(function ($) {

    "use strict";

	
	$('html').addClass('oflow-hidden');

    $(window).bind("load", function() {
		
		$('.navigation').stickySidebar({
			containerSelector: 'section',
			topSpacing: 150,
			bottomSpacing: 0
		});
		
		
		$("#preloader").fadeOut();
	   $('html').removeClass('oflow-hidden');
    });
	
	
	$('.img-wrapper').each(function(){
		
		var $this = $(this),
			text = $this.data('title');
		
		if((text != null)){
			$this.append('<h6 class="img-title">' + text + '</h6>');
		}
		
	});
	
	
    $('.click-copy').on('click', function(){
        var $this = $(this),
            copyText = $this.text();

        copyClip(copyText)
        $this.addClass('active').addClass('copied');

        setTimeout(function(){ $this.removeClass('active'); },100);
        setTimeout(function(){ $this.removeClass('copied'); },800);
    });


    $('.nav-title').on('click', function(){
        $('.navigation-menu').toggleClass('active');
        $('.navigation .nav-title').addClass('active');
    });


    // // PAGE LOADER
    $(window).on('load', function () {

        if (isExists(".loader-wrapper")) {
            $(".loader-wrapper").fadeOut("slow");
        }

    });


    // Dropdown functionality
    var winWidth = $(window).width();
    var isTouchDevice = (winWidth < 767) ? true : false;
    menuDropdown(isTouchDevice);

    // Window resize event for dropdown menu
    $(window).on('resize', function () {

        winWidth = $(window).width();
        isTouchDevice = (winWidth < 767) ? true : false;

        debounceFnForDropDown(isTouchDevice);
    });


    // SMOOTH SCROLL ON THE SAME PAGE AFTER CLICKING A LINK
    smoothScroll();


    // FIXED MENU ON SCROLL
    var $fixedMenu

    if (isExists('.fixed-on-scroll')) {
        $fixedMenu = $('.fixed-on-scroll');
        fixedMenuOnScrollFn($fixedMenu);
    }


    // Enable counter on visible
    var a = 0;



    

    // SCROLL EVENT
    $(window).on('scroll', function () {

        var winTop = $(window).scrollTop(),
            winMid = $(window).scrollTop() + 200;

        /*VISIBLE SELECTION*/
        $('a[href^="#"]').removeClass('active');
        $('article').each(function(){
            var $this = $(this),
                elemBottom = $this.offset().top + $this.outerHeight();
            if(($this.offset().top < winMid) && (elemBottom > winMid)){
                var elem = 'a[href="#' + $this.attr('id') + '"]';
                $(elem).addClass('active');
            }
        });

        /*FIXED MENU*/
        if((winTop >= 60))  $('.navigation-fixed').addClass('fixed');
        else $('.navigation-fixed').removeClass('fixed');


        // FIXED MENU ON SCROLL
        if (typeof $fixedMenu != 'undefined') {
            fixedMenuOnScrollFn($fixedMenu);
        }

        // BACK TO TOP VISIBLE AFTER THE CENTER OF THE PAGE
        backToTop();


    });


	// Prevent fire an event if anchor is null
	$('a[href="#"]').on('click', function () {
		return false;
	});

	// ENABLE MENU ICON FOR SMALLER DEVICE
	$('[data-menu-visible]').on('click', function (event) {

		var $this = $(this),
			visibleHeadArea = $this.data('menu-visible');

		$('.nav-visible').not(visibleHeadArea).removeClass('visible');

		$(visibleHeadArea).toggleClass('visible');

		event.stopPropagation();
	});

	// BACK TO TOP VISIBLE AFTER THE CENTER OF THE PAGE
	backToTop();

	$('#back-to-top').on('click', function (e) {
		e.preventDefault();
		$('html,body').animate({
			scrollTop: 0
		}, 700);
	});

})
(jQuery);


// FIXED MENU ON SCROLL
function fixedMenuOnScrollFn($fixedElem) {

    var topWindow = $(window).scrollTop();
    var fixedPosition = 500;

    if ((fixedPosition) < topWindow) {
        $fixedElem.addClass('fixed');
    } else if ((fixedPosition) > topWindow) {
        $fixedElem.removeClass('fixed');
    }
}

// DROPDOWN MENU FUNCTION
function menuDropdown(isTouchDevice) {

    $('.main-menu li').removeClass('d-hover-effect');

    if (isTouchDevice) {

        $('.main-menu  a').unbind('click').on('click', function (e) {

            var listElem = $(this).parent();

            $(listElem).toggleClass('d-hover-effect');

        });

    } else {

        var anchorElem = $('.main-menu li');

        $(anchorElem).unbind('mouseenter').on('mouseenter', function (e) {
            $(this).addClass('d-hover-effect');

        }).unbind('mouseleave').on('mouseleave', function (e) {
            $(this).removeClass('d-hover-effect');

        });
    }

}


// Debounce Function for Dropdown menu
var debounceFnForDropDown = debounce(function (isTouchDevice) {
    menuDropdown(isTouchDevice);
}, 50);


function smoothScroll() {
    // Select all links with hashes
    $('a[href*="#"]')
    // Remove links that don't actually link to anything
        .not('[href="#"]')
        .not('[href="#bs-carousel"]')
        .not('[href="#0"]')
        .click(function (event) {
            // On-page links
            if (
                location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '')
                &&
                location.hostname == this.hostname
            ) {
                // Figure out element to scroll to
                var target = $(this.hash);
                target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
                // Does a scroll target exist?
                if (target.length) {
                    // Only prevent default if animation is actually gonna happen
                    event.preventDefault();
                    $('html, body').animate({
                        scrollTop: target.offset().top - 80
                    }, 500, function () {
                        // Callback after animation
                        // Must change focus!
                        var $target = $(target);
                        $target.focus();

                    });
                }
            }
        });
}


function backToTop() {
    var scrollTrigger = $(document).outerHeight() / 2;
    var scrollTop = $(window).scrollTop();
    if (scrollTop > scrollTrigger) {
        $('#back-to-top').addClass('show');
    } else {
        $('#back-to-top').removeClass('show');
    }
};

// Debounce Function for Dropdown menu
var debounceFnForDropDown = debounce(function (isTouchDevice) {
    menuDropdown(isTouchDevice);
}, 50);

// Debounce Function
function debounce(func, wait, immediate) {
    var timeout;
    return function () {
        var context = this, args = arguments;
        var later = function () {
            timeout = null;
            if (!immediate) func.apply(context, args);
        };
        var callNow = immediate && !timeout;
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
        if (callNow) func.apply(context, args);
    };
}


function isExists(elem) {
    if ($(elem).length > 0) {
        return true;
    }
    return false;
}


