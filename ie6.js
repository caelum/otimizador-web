var IS_IE6 = true;

/**
 * jQuery (PNG Fix)
 * Microsoft Internet Explorer 24bit PNG Fix
 * Under  The MIT License
 * http://plugins.jquery.com/project/iepngfix
 */
(function($) {
	$.fn.pngfix = function(options) {
		
		// ECMA scope fix
		var elements 	= this;
		// Plug-in values
		var settings 	= $.extend({
			imageFixSrc: 	false
		}, options);
		
		if(!$.browser.msie || ($.browser.msie &&  $.browser.version >= 7)) {
			return(elements); // Kill
		}
		
		function setFilter(el, path, mode) {
			// Apply filter to element, setting the MSDN properties:
			//		:src
			//		:enabled
			//		:sizingMethod  
			var fs 			= el.attr("filters");
			var alpha 		= "DXImageTransform.Microsoft.AlphaImageLoader";
			if (fs[alpha]) {
				with (fs[alpha]) { 
					enabled = true;
					src = path; 
					sizingMethod = mode;
			 	}
			} else {
				el.css("filter", 'progid:' + alpha + '(enabled="true", sizingMethod="' + mode + '", src="' + path + '")');			
			}
		}
		
		function forceWidth(el) {
			if(el.css("width") == "auto" & el.css("height") == "auto") {
				// Only force width of element if it's set to auto
				el.css("width", el.attr("offsetWidth") + "px");
			}
		}
		
		// __APPLY__
		
		return(
			elements.each(function() {
				
				var el = $(this);
				
				if(el.attr("tagName").toUpperCase() == "IMG" && (/.png"?$/).test(el.attr("src"))) {
					
					if(!settings.imageFixSrc) {
						// Wrap the <img> in a <span> then apply style/filters, 
						// removing the <img> tag from the final render 
						el.wrap("<span class=iepngfix></span>");
						var par = el.parent();
						par.css({
							height: 	el.height(),
							width: 		el.width(),
							display: 	"inline-block"
						});
						setFilter(par, el.attr("src"), settings.repeatMethod != null? settings.repeatMethod :"scale");
						el.remove();
					} else if((/.gif$/).test(settings.imageFixSrc)) {
						// Replace the current image with a transparent GIF
						// and apply the filter to the background of the 
						// <img> tag (not the preferred route)
						forceWidth(el);
						setFilter(el, el.attr("src"), "image");
						el.attr("src", settings.imageFixSrc);
					}
					
				} else {
					var bg = el.css("backgroundImage");
					var matches = bg.match(/^url\("(.*)"\)$/);
					
					if(matches != null) {
						// Elements with a PNG as a backgroundImage have the
						// filter applied with a sizing method relevant to the 
						// background repeat type
						forceWidth(el);
						el.css("backgroundImage", "none");
						
						// Restrict scaling methods to valid MSDN defintions (or one custom)
						if(el.css("backgroundRepeat").indexOf("repeat") > -1) {
							var sc = settings.repeatMethod == "repeat" ? "repeat" : "scale";
						} else {
							var sc = "crop";
						}
						
						setFilter(el, matches[1], settings.repeatMethod != null? settings.repeatMethod : sc);
						
						// IE peek-a-boo for internal links
						el.find("a").each(function() {
							$(this).css("position", "relative");
						});
					}
				}
				
				
			}) // __END__
		);
	}

})(jQuery);

$(function() {
	/* carrega o hover para IE6 */
	$('<style type="text/css">body { behavior:url("/javascripts/ie6/csshover3.htc"); }</style>')
	.appendTo('head');
	
	/* Aplica os hacks do IE */
	$('.logocaelum img').pngfix();
	$('body').css('background', '#fff');
	
	/* barra de warning */
	$('<p/>').html('O Internet Explorer 6 não é mais suportado no Site da Caelum, <b>você poderá encontrar alguns problemas durante a navegação</b>').css({
		background: '#FFEC8B',
		borderBottom: '1px solid #666',
		display: 'none',
		margin: 0,
		padding: '10px'
	}).prependTo('body').slideDown('slow');
});
