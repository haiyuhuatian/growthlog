jQuery(function(){
    var $ = jQuery;
    if( !window.webuploader ){
        console.log("请配置好window.webuploader");
        $('#dndArea p').html('请配置好window.webuploader');
        return false;
    }
    if( !window.webuploader.config || !window.webuploader.config.wrapId){
        console.log("请配置好window.webuploader.config.wrapId");
        $('#dndArea p').html('请配置好window.webuploader.config.wrapId');
        return false;
    }
    if( !window.webuploader.uploadUrl ){
        console.log("请配置好window.webuploader.uploadUrl");
        $('#dndArea p').html('请配置好window.webuploader.uploadUrl');
        return false;
    }
    if( !window.webuploader.updateUrl ){
        console.log("请配置好window.webuploader.updateUrl");
        $('#dndArea p').html('请配置好window.webuploader.updateUrl');
        return false;
    }
    if( !window.webuploader.removeUrl ){
        console.log("请配置好window.webuploader.removeUrl");
        $('#dndArea p').html('请配置好window.webuploader.removeUrl');
        return false;
    }
    var disX = 0;
    var disY = 0;
    var minZindex = 1;
    var origin;
    var is_moveing = false;
    var $wrap = $('#' + window.webuploader.config.wrapId);
    var $queue = $('<ul class="filelist"></ul>').appendTo( $wrap.find('.queueList'));
    var $statusBar = $wrap.find('.statusBar');
    var $info = $statusBar.find('.info');
    var $upload = $wrap.find('.uploadBtn');
    var $placeHolder = $wrap.find('.placeholder');
    var $progress = $statusBar.find('.progress').hide();
    var fileSize = 0;
    var state = 'pedding';
    var percentages = {};
    var supportTransition = (function(){
        var s = document.createElement('p').style,
            r = 'transition' in s ||
                'WebkitTransition' in s ||
                'MozTransition' in s ||
                'msTransition' in s ||
                'OTransition' in s;
        s = null;
        return r;
    })();
    // 优化retina, 在retina下这个值是2
    var ratio = window.devicePixelRatio || 1;
    // 缩略图大小
    var thumbnailWidth = window.webuploader.config.thumbWidth || 110;
    thumbnailWidth *= ratio;
    var thumbnailHeight = window.webuploader.config.thumbHeight || 110;
    thumbnailHeight *= ratio;
    var uploader = WebUploader.create({
        swf: "Uploader.swf",
        server: window.webuploader.uploadUrl,
        pick: {
            id:'#filePicker',
            label: '点击选择图片'
        },
        dnd: '.queueList',
        paste: document.body,
        accept:{
            title: 'Images',
            extensions: 'gif,jpg,jpeg,png',
            mimeTypes: 'images/*'
        },
        resize: false,
        disableGlobalDnd: true,
        chunked: true,
        fileNumLimit: 100
    });


  //更新服务端附件
    function updateServerFiles(){
        var postData = {};
        $('[data-src="server"]').each(function(index, obj){
            postData[$(obj).attr('data-key')] = $(obj).attr('data-sort');
        });
        $.ajax({
            type:'post',
            url: '${base}/member/diary!myTest.action',
            data: postData,
            dataType:'json',
            success:function(data){
                //setState('finish');
                alert('更新成功');
                $upload.removeClass('disabled');
                setState('ready');
                uploader.reset();
            }
        });
    }
    function addFile(file) {
    	if(fileCount < 10) {
	    	if( file.src == "client"){
	    		if( file.getStatus() != 'invalid'){
	    			   uploader.makeThumb( file, function(error, src){
	                       if( error ){
	                           return ;
	                       }
	                   	   var img = $('<div class="photo" id="myImage_'+imgNum+'"><img  src="'
	                   			   +src
	                   			   +'" /><div class="deletePhoto" onclick="deletePhoto($(this))"><p></p></div></div>');
	                   	   $("#clearDiv").before(img);
	                   	   imgNum++;
	                      /* $.post("diary!savePhoto.action",{"imageSrc":src},function(data){
	                    	   $("#myImage_"+imgNum).attr("src",data);
	                    	   imgNum++;
	                       });*/
	                   }, -1, -1);
	            }
	    	}
    	}
    }

    //添加附件到webuploader中
    function addFile2( file ){
        var index = $queue.find('li').length;
        var imgLeft = index * (thumbnailWidth+10);
        var imgTop = 0;
        var wrapHeight = thumbnailHeight+20;
        var wrapWidth = $queue.width() + 20 + 10;
        if( imgLeft >= wrapWidth){
            imgTop = parseInt(imgLeft/wrapWidth) * (thumbnailHeight+10);
            wrapHeight = imgTop + wrapHeight;
            imgLeft = (index % (parseInt(wrapWidth/(thumbnailWidth+10)) ) ) * (thumbnailWidth+10);
        }
        if(window.webuploader.config.theAgent == 'pc'){
        	 $queue.height(wrapHeight);
        }else {
        	 $queue.height(thumbnailHeight*3+20);
        }
        var $li = $('<li data-key="'+file.key+'"  data-src="'+file.src+'" data-sort="'+index+'" id="' + file.id + '" style="margin:0;cursor:move;width:'+thumbnailWidth+'px;float:left;margin-left:3px;margin-top:5px;">' +
        		'<input type="hidden" id="input_'+file.id+'" value="'+imgNum+'" />'+
                '<p class="title">' + file.name + '</p>' +
                '<p class="imgWrap"></p>' + 
                '<p class="progress"><span></span></p>' + '</li>'
            ),
            $btns = $('<div class="file-panel">' +
                '<span class="cancel">删除</span>').appendTo( $li ),

            $progess = $li.find('p.progress span'),
            $wrap = $li.find('p.imgWrap'),
            $info = $('<p class="error"></p>'),

            showError = function( code ){
                switch( code ){
                    case 'exceed_size':
                        text = '文本大小超出';
                        break;
                    case 'interrupt':
                        text = '上传暂停';
                        break;
                    default:
                        text = '上传失败';
                        break;
                }
                $info.text( text ).appendTo( $li );
            };

        if( file.src == "client"){
            if( file.getStatus() == 'invalid'){
                showError( file.statusText );
            } else {
                $wrap.text('预览中');
                uploader.makeThumb( file, function(error, src){
                    if( error ){
                        $wrap.text('不能预览');
                        return ;
                    }
                    
                }, thumbnailWidth, thumbnailHeight);
                percentages[ file.id ] = [ fileSize, 0];
                file.rotation = 0;
            };
            file.on('statuschange', function(cur, prev){
                if( prev == 'progress'){
                    $progress.hide().width(0);
                } else if( prev == 'queued'){
                    $li.off('mouseenter mouseleave');
                    $btns.remove();
                }

                if( cur == 'error' || cur == 'invalid'){
                    showError( file.statusText );
                    percentages[ file.id][ 1 ] = 1;
                } else if( cur == 'interrupt'){
                    showError('interrupt');
                } else if( cur == 'queued'){
                    percentages[ file.id ][1] = 0;
                } else if( cur == 'progress'){
                    $info.remove();
                    $progress.css('display', 'block');
                } else if( cur == 'complete' ){
                    $li.append('<span class="success"></span>');
                }
                $li.removeClass('state-'+prev).addClass('state-'+cur);
            });
        }
        else{
            var img = $('<img  src="'+file.path+'">');
            //img.bind('load',setDragEvent);
            $wrap.empty().append( img );
        }

        $li.on('mouseenter', function(){
            $btns.stop().animate({height:30});
        });
        $li.on('mouseleave', function(){
            $btns.stop().animate({height:0})
        });

        $btns.on('click', 'span', function(){
        	if( file.src == "client")
                uploader.removeFile( file );
            else{
                removeServerFile( file );
                $('#'+file.id).remove();
            }
        });
        $li.appendTo( $queue );
    }

    //删除webupload中的图片
    function removeFile( file ){
    	var myId = $("#input_"+file.id).val();
    	var imgImg = $("#myImage_"+myId);
    	imgImg.remove();
        var $li = $('#'+file.id);
        delete percentages[ file.id ];
        updateTotalProgress();
        $li.off().find('.file-panel').off().end().remove();
    }

    //更新webuploader中图片上传的进度
    function updateTotalProgress(){
        var loaded = 0,
            total = 0,
            spans = $progress.children(),
            percent;

        $.each( percentages, function(k,v){
            total += v[0];
            loaded += v[0] * v[1];
        });

        percent = total? loaded /total : 0;

        spans.eq(0).text(Math.round(percent*100)+'%');
        spans.eq(1).css('width', Math.round(percent*100)+'%');
        updateStatus();
    }

    //更新webuploader中的状态
    function updateStatus(){
        var text = '', stats;
        text = '选中<span id="fileAcount">'+fileCount + '</span>张图片，（共能上传9张照片）';
        $info.html(text);
    }

    //设置webuploader的状态
    function setState(val){
        var file,stats;
        if( val == state){
            return ;
        }
        $upload.removeClass('state-'+state);
        $upload.addClass('state-'+val);
        state = val;

        switch( state ){
            case 'pedding':
                $placeHolder.removeClass('element-invisible');
                $queue.parent().removeClass('filled');
                $queue.hide();
                $statusBar.addClass('element-invisible');
                uploader.refresh();
                break;
            case 'ready':
                $placeHolder.addClass('element-invisible');
                $('#filePicker2').removeClass('element-invisible');
                $queue.parent().addClass('filled');
                $queue.show();
                $statusBar.removeClass('element-invisible');
                uploader.refresh();
                break;
            case 'uploading':
                $('filePicker2').addClass('element-invisible');
                $progress.show();
                $upload.text('暂停上传');
                break;
            case 'paused':
                $progress.show();
                $upload.text('继续上传');
                break;
            case 'confirm':
                $progress.hide();
                $upload.text('开始上传').addClass('disabled');
                stats = uploader.getStats();
                if( stats.successNum && !stats.uploadFailNum ){
                    setState( 'finish' );
                    return ;
                }
                break;
            case 'finish':
                stats = uploader.getStats();
                if( stats.successNum ){
                    alert('上传成功');
                } else {
                    state = 'done';
                    location.reload();
                }
                break;
        }
        updateStatus();
    }

    //文件加入到webuploader中的队列
    function fileQueue(file){
    	if(fileCount <9){
    		file.src = file.src || "client";
            fileCount++;
            fileSize += file.size;

            if( fileCount == 1){
                $placeHolder.addClass('element-invisible');
                $statusBar.show();
            }

            addFile( file );
            setState( 'ready' );
            updateTotalProgress();
    	}
    }


    if( !WebUploader.Uploader.support() ) {
        console.log('WebUploader 不支持');
        throw new Error('WebUploader does not support');
    }

    uploader.addButton({
        id: '#filePicker2',
        label: '继续添加',
    });


    uploader.on('uploadProgress', function( file, percentage){
        var $li = $('#' + file.id),
        $percent = $li.find('.progess span');
        
        $percent.css( "width", percentage * 100 + '%');
        updateTotalProgress();
    });


    uploader.on('fileQueued', fileQueue);

    uploader.on('fileDequeued', function(file){
        fileCount --;
        fileSize -= file.size;
        if( !fileCount){
            setState('pedding');
        }
        removeFile( file );
        updateTotalProgress();
    });

    uploader.on('uploadSuccess', function(file){
        $('#' + file.id ).find('p.state').text('已上传');
    });

    uploader.on('uploadError', function(file){
        console.log(file.id + '上传出错');
    });

    uploader.on('uploadComplete', function(file){
        $('#' + file.id ).find('p.state').fadeOut();
    });

    uploader.on('all', function( type ){
        if( type == 'uploadFinished') {
            setState('confirm');
        } else if( type == 'startUpload' ){
            setState('uploading');
        } else if( type == 'stopUpload' ){
            setState('paused');
        }
    });

    uploader.on('uploadBeforeSend', function(block, data){
        data.sort = $('#'+data.id).attr('data-sort');
    });

    $upload.on('click', function(){
        uploader.sort(function(obj1, obj2){
            return $('#'+obj1.id).attr('data-sort') > $('#'+obj2.id) ? -1: 1;
        });
        if( $(this).hasClass('disabled')){
            return false;
        }
        if( state == 'ready'){
            if(uploader.getFiles().length < 1)
                updateServerFiles();
            else
                uploader.upload();
        } else if(state == 'paused'){
            uploader.upload();
        } else if( state == 'uploading'){
            uploader.stop();
        }
    });

    $info.on('click', '.retry', function(){
        uploader.retry();
    });

    $info.on('click', '.ignore', function(){
        alert('todo');
    });

    $upload.addClass('state-'+state);
});

