this.LinkPreview = {
	contextPath : contextPath,
	previewId : "link-preview",
	mouseOver : false,
	intervalTime : 700,
	xOffset : 5,
	yOffset : 14,
	pageX : 0,
	pageY : 0,
	lastPreview : "",
	loaded : false,
	needLoading  : true,
	clicked : false,
	eventListener : function(e) {
		if (LinkPreview.lastPreview === this) {
			return;
		}

		LinkPreview.needLoading = true;
		LinkPreview.clicked = false;
		AJS.$("div#" + LinkPreview.previewId).remove();
		AJS.$("#" + LinkPreview.previewId).remove();
		AJS.$("#" + LinkPreview.previewId + "-load").remove();
		LinkPreview.lastPreview = this

		var c = "";
		this.t = this.title;
		var contentType = "";
		if (LinkPreview.showName) {
			c = (this.t != "") ? "" + this.t : "";
		}
		
		// if the preview is in the <span> then the href is on the parent node
		if(this.href === undefined){
			this.href = AJS.$(this)[0].parentNode.href;
		}
		
		var urlRequest = "";
		urlRequest = contextPath
				+ '/rest/link-preview/1.0/link/resolve?urlSource='
				+ encodeURIComponent(this.href);

		if (this.getAttribute('data-type') !== null) {
			LinkPreview.processPreview(this.getAttribute('data-type'),
					this.href, c, e);
		} else {
			var urlRequest = contextPath
					+ '/rest/link-preview/1.0/link/resolve?urlSource='
					+ encodeURIComponent(this.href);
			$.ajax({
				url : urlRequest,
				type : 'get',
				dataType : 'json',
				async : true,
				success : function(data) {
					LinkPreview.processPreview(data.objType, data.link, c,
							e);
				}
			});

		}
	},
	
	showClickToPreviewIcon: function(e) {
		AJS.$("body").append(
						"<div id='"
								+ LinkPreview.previewId
								+ "' style='background-color: rgba(255, 255, 255, 0.1);border:0px;z-index:5006'><span "
								+ "class='aui-icon aui-icon-large aui-iconfont-view'style='color:"
								+ AJS.$("#header").css("background-color")
								+ "'> View </span></div>");

		LinkPreview.pageX = e.pageX;
		LinkPreview.pageY = e.pageY;
		LinkPreview.setPosition(LinkPreview.pageX, LinkPreview.pageY, LinkPreview.previewId);

//		clearInterval(LinkPreview.timerEvent);
//		LinkPreview.timerEvent = setInterval(function() {
//			LinkPreview.timerEventFunction();
//		}, LinkPreview.intervalTime);
		
		AJS.$("#" + LinkPreview.previewId).on("mouseleave",
				LinkPreview.mouseLeaveEventFunction);

		
		AJS.$("#" + LinkPreview.previewId).click(function() {
			AJS.$("div#" + LinkPreview.previewId).remove();
			LinkPreview.clicked = true;
			LinkPreview.showPreviewLink(e);
		});
	},

	processPreview : function(dataType, dataLink, c, e) {

		if (dataType != null && dataLink != null) {
			if (dataType === "-1") {
				console.log("cant open this one");
				return;
			}
			dataType = LinkPreview.cleanDataType(dataType);
			if (LinkPreview.pdfPreview && dataType === "application/pdf") {
				if (LinkPreview.clickToPreview) {
					LinkPreview.showClickToPreviewIcon(e);
				}else{
					LinkPreview.clicked = true;
					LinkPreview.showLoadingPreview(e);
				}
				if (LinkPreview.checkSameOrigin(dataLink)) {
					AJS.$("body").append(
							"<p class='pdf' id='"
									+ LinkPreview.previewId + "'><iframe src='"
									+ LinkPreview.PDFViewerURL
									+ encodeURIComponent(dataLink)
									+ "' onLoad='LinkPreview.previewHasLoaded()'></iframe><br/>" + c + "</a>");
				} else {
					AJS.$("body").append(
							"<p class='pdf' id='"
									+ LinkPreview.previewId + "'><iframe src='"
									+ LinkPreview.PDFViewerURL
									+ encodeURIComponent(dataLink)
									+ "'onLoad='LinkPreview.previewHasLoaded()'></iframe><br/>" + c + "</a>");
				}
			} else if (LinkPreview.imageTypes.indexOf(dataType) > -1) {
				if (LinkPreview.clickToPreview) {
					LinkPreview.showClickToPreviewIcon(e);
				}else{
					LinkPreview.clicked = true;
					LinkPreview.showLoadingPreview(e);
				}
				AJS.$("body").append(
						"<p class='image' id='"
								+ LinkPreview.previewId + "'><img src='"
								+ dataLink + "' alt='" + c + "'onLoad='LinkPreview.previewHasLoaded()' /><br/>" + c
								+ "</a>");
			} else if (LinkPreview.otherTypes.indexOf(dataType) > -1) {
				if (LinkPreview.clickToPreview) {
					LinkPreview.showClickToPreviewIcon(e);
				}else{
					LinkPreview.clicked = true;
					LinkPreview.showLoadingPreview(e);
				}
				var newUrl = dataLink;
				if (dataLink.indexOf("?") > -1) {
					newUrl += "&disablePreview=true";
				} else {
					newUrl += "?disablePreview=true";
				}
				AJS.$("body").append(
						"<p class='other' id='" + LinkPreview.previewId
								+ "'><iframe id=\"linkPreviewIframe\" src='"
								+ newUrl + "'onLoad='LinkPreview.previewHasLoaded()'></iframe></a>");
			}
		}
		return;
	},
	
	showPreviewLink : function (e) {
		if (LinkPreview.needLoading) {
			LinkPreview.showLoadingPreview(e);
		} else {
		
		LinkPreview.pageX = e.pageX;
		LinkPreview.pageY = e.pageY;
		LinkPreview.setPosition(LinkPreview.pageX, LinkPreview.pageY, LinkPreview.previewId);

		// Apply header color
		if (LinkPreview.useHeader && AJS.$("#" + LinkPreview.previewId) != null
				&& AJS.$("#header").css("background-color") != null) {
			AJS.$("#" + LinkPreview.previewId).css("background",
					AJS.$("#header").css("background-color"));
		}
		
		AJS.$("#" + LinkPreview.previewId).on("mouseleave",
				LinkPreview.mouseLeaveEventFunction);
		
		}
	},
	

		showLoadingPreview : function(e) {
		// spinning wheel
		if (!LinkPreview.needLoading) {
			return;
		}
		AJS.$("body").append("<div id='"
					+ LinkPreview.previewId
					+ "-load' style='position:absolute;display:block;padding:8px;z-index:5006'> " // z-index to stay infront of sidebar
					+"<span class='aui-icon aui-icon-large aui-icon-wait'>Loading...</span> <br/></a>");
		LinkPreview.pageX = e.pageX;
		LinkPreview.pageY = e.pageY;
		LinkPreview.setPosition(LinkPreview.pageX, LinkPreview.pageY,
				LinkPreview.previewId + "-load");

		AJS.$("#" + LinkPreview.previewId + "-load").on("mouseleave",
				LinkPreview.mouseLeaveEventFunction);
	},

	previewHasLoaded: function(e) {
        LinkPreview.needLoading = false;
		
		AJS.$("#" + LinkPreview.previewId + "-load").remove();
		
		if(!LinkPreview.clicked){
			return;
		}
		
		LinkPreview.setPosition(LinkPreview.pageX, LinkPreview.pageY, LinkPreview.previewId);
		
		if(LinkPreview.useHeader && AJS.$("#" + LinkPreview.previewId) != null && AJS.$("#header").css("background-color") != null){
			AJS.$("#" + LinkPreview.previewId).css("background", AJS.$("#header").css("background-color"));
		}
		
	},

	cleanDataType : function(dataType) {
		return dataType.split(";")[0];
	},

	createPDF : function(link , c) {
		urlRequest = contextPath + '/plugins/servlet/pdf-viewer?file='
				+ encodeURIComponent(link);
		$.ajax({
			url : urlRequest,
			type : 'get',
			async : false,
			success : function(data) {
				
				var el = document.createElement( 'html' );
				el.innerHTML = data;
				
				var scripts = el.getElementsByTagName('head')[0].getElementsByTagName('script');
				var links = el.getElementsByTagName('head')[0].getElementsByTagName('link');
				
				jQuery.each(  links , function (i, val) {
					AJS.$("head").append(val.outerHTML);
				});
				
				jQuery.each(  scripts , function (i, val) {
					
					var script   = document.createElement("script");
					script.type  = val.type;
					script.src   = val.src;
					script.text  = val.text;
					document.head.appendChild(script);
				});
				
				AJS.$("body").append("<p class='pdf' id='"
							+ LinkPreview.previewId + "'><iframe srcdoc='" + el.getElementsByTagName('body')[0].innerHTML +"'  "
							+ "></iframe><br/>" + c + "</a>");
			}
		});
	},

	process : function() {
		var params = window.location.href.split("?");
		var skipPreview = false;
		if(params.length > 1) {
			params = params[1].split("&");
			params.forEach(function(param) {
				var paramValue = param.split("=");
				if (paramValue[0] === "disablePreview"
						&& paramValue[1] === "true") {
					skipPreview = true;
				}
			});
		}
		var addEventOnDocument = true;
		var ele;
		var applyElement = "";
		if (!skipPreview && this.applyElements != null && this.applyElements.length > 0) {
			jQuery.each(this.applyElements, function() {
				addEventOnDocument = true;
				applyElement = this.toString();
				console.log(this.toString());
				jQuery.each(AJS.$(this.toString()), function() {
					// adds data-type to attachments
					var oldThis = this;
					if(LinkPreview.checkSameOrigin(this) && this.getAttribute("data-type") == null){
						jQuery.each(LinkPreview.attachments, function() {
							if(oldThis.getAttribute("href").indexOf(this.url) > 0){
								oldThis.setAttribute("data-type",this.type);
							}
						});
					}
					
					if(this.childNodes.length == 2) {
						// for sidebar so the preview is only when you hover the link and not the a element
						ele = AJS.$(this.lastChild);
						//this.lastChild.setAttribute("href",this.href);
						ele.on("mouseenter", LinkPreview.eventListener);
						ele.on("mouseleave",LinkPreview.mouseLeaveEventListener);
						addEventOnDocument = false;
					}else {
						ele = AJS.$(this);
					}
					
					if (!LinkPreview.fixedImagePreview) {
						ele.mousemove(function(e) {
							// SetPosition
							LinkPreview.pageX = e.pageX;
							LinkPreview.pageY = e.pageY;
							
							if(LinkPreview.clicked){
								if(LinkPreview.needLoading){
									LinkPreview.setPosition(LinkPreview.pageX, LinkPreview.pageY, LinkPreview.previewId + "-load");
								}else{
									LinkPreview.setPosition(LinkPreview.pageX, LinkPreview.pageY, LinkPreview.previewId);
								}
							}
						});
					}
				});
				if(addEventOnDocument){
					AJS.$(document).on("mouseenter",applyElement,LinkPreview.eventListener);
					AJS.$(document).on("mouseleave",applyElement,LinkPreview.mouseLeaveEventListener);
				}
			});
			AJS.$(document).on("mouseleave","#" + LinkPreview.previewId,LinkPreview.mouseLeaveEventListener);
			AJS.$(document).on("mouseleave","#" + LinkPreview.previewId + "-load",LinkPreview.mouseLeaveEventListener);
		}
	},
		
	timerEvent : 0,
	timerEventFunction : function() {
		if (!AJS.$(LinkPreview.lastPreview).is(":hover")
				&& !AJS.$("#" + LinkPreview.previewId).is(":hover")
				&& !AJS.$("#" + LinkPreview.previewId + "-load").is(":hover")) {
			AJS.$("#" + LinkPreview.previewId).remove();
			AJS.$("#" + LinkPreview.previewId + "-load").remove();
			LinkPreview.lastPreview = "";
			LinkPreview.clicked = false;
			LinkPreview.needLoading = true;
		}
	},
	
	mouseLeaveEventListener : function() {
		if (!LinkPreview.loaded && LinkPreview.xhr !== undefined && LinkPreview.xhr.status !== 0) {
			console.log("aborted ajax");
			LinkPreview.xhr.abort();
			LinkPreview.lastPreview = "";
			LinkPreview.clicked = false;
			LinkPreview.needLoading = true;
			LinkPreview.xhr = undefined;
		} else {
			setTimeout(LinkPreview.timerEventFunction, LinkPreview.intervalTime);
		}
	},

	checkSameOrigin : function(url) {
		var loc = window.location, a = document.createElement('a');
		a.href = url;
		return a.hostname == loc.hostname && a.port == loc.port
				&& a.protocol == loc.protocol;
	},

	setPosition : function(pageX, pageY, id) {
		// Set position
		var linkPreviewH = AJS.$("#" + id).height();
		var linkPreviewW = AJS.$("#" + id).width();
		var windowH = AJS.$(window).height();
		var windowW = AJS.$(window).width();

		var offsetX = 0;
		var offsetY = 0;
		if (windowW - pageX >= linkPreviewW + LinkPreview.xOffset) {
			offsetX = pageX + LinkPreview.xOffset;
		} else if (pageX - LinkPreview.xOffset > linkPreviewW) {
			offsetX = pageX - (linkPreviewW + LinkPreview.xOffset);
		} else {
			offsetX = pageX + LinkPreview.xOffset;
		}

		if (windowH - pageY >= linkPreviewH + LinkPreview.yOffset) {
			offsetY = pageY - LinkPreview.yOffset;
		} else if (pageY - LinkPreview.yOffset >= linkPreviewH) {
			offsetY = pageY - (linkPreviewH + LinkPreview.yOffset);
		} else {
			offsetY = pageY - LinkPreview.yOffset;
		}

		AJS.$("#" + id).css("top", (offsetY) + "px").css(
				"left", (offsetX) + "px").fadeIn("slow");
	},
	

};
