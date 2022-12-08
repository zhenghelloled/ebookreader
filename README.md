# ebookreader
1、项目介绍	

计算机技术的不断发展带来的是数字化时代的加速来临。在现在的智能终端上，人们已经可以实现听音乐、玩游戏、看电影等活动。在这期间，数字化阅读的需求也呈现爆炸式的增长，这给不少互联网企业带来商机。于是加入电子书行业的企业如雨后春笋一般浮现出来。在这期间，飞速成长的电子书行业也反过来对我们的生活产生了巨大的冲击。作为新时代的产物，电子书不仅仅对传统的图书行业产生了冲击，而且对现代人们的生活习惯和学习方式也产生了巨大的影响。未来，电子书带来的改变将会持续不断地渗透进我们的生活。

随着手机阅读的用户持续增加，开始在网上自主写作的人也越来越多，可见，手机阅读这个市场在逐步扩大。但想要在电子书行业站稳脚跟，一款电子书APP必须能够满足用户的需求。在主流的手机阅读软件里，普遍存在的问题是功能复杂导致界面不够清晰，软件占用的资源太多从而使用户不能在网页浏览电子书。而在现在快节奏的生活里，追求快捷快速是用户的极大诉求。因此，ebookreader作为一款轻量级的APP应运而生，能够很好地满足大众手机阅读用户的需求。


2、软件功能

ebookreader是一款简洁易操作的轻量级的电子阅读APP。作为一个电子阅读APP，最主要的功能即使提供阅读，让软件能在移动终端很好地呈现出文字信息，从而还原出人们在平时生活中的阅读纸质书籍的感受。本APP对于软件的整体交互设计都采用十分简单的设计。譬如文件的读取采用一个清晰明了的列表来完成。在电子书的显示方面，本文采用的是深色的背景地板，这样能极大程度地还原传统书籍的质感。

APP提供的服务如下：

（1）最近阅读。主要用于保存用户最近阅读过的图书。

（2）本地书库。与现实当中的界面有着异曲同工之妙，本地书库是用来展示用户已经添加到书架中的书。主要是管理用户的所有图书，有配套的图书增删改查功能。

（3）本地搜索。考虑到用户主要用微信接受本地的电子书，所以将本地设备中微信下载的文件夹中的文件显示在列表中，可以选择其中的txt格式的电子书加入书架。

（4）网上书城。目前主要设置了起点网，可以在线阅读海量电子书。

（5）我的笔记。主要可以记录读书时的想法，有配套的笔记增删功能。


3、部署要求

需Android2.0以上版本。


4、代码组织架构

	# 目录结构

	|----- Readme.md

	|----- /MyEbookReader 		工程目录
	|----- /MyEbookReader/src	源码目录
	|----- /com/ketai/readdal/
	|----- MyContentProvider.java		数据库的初始工作
	|----- ReadSqliteOpenHelper.java	数据库表格生成
	|----- /com/ketai/reader/
	|----- DrawWaterWave.java			自定义波纹控件
	|----- LabelList.java				书签界面
	|----- LocalityRead.java			界面 本地书库
	|----- LocalitySearch.java			界面 本地搜索
	|----- Main.java					界面 我的笔记
	|----- MyMainActivity.java			主界面容器
	|----- NoteCheck.java				界面 查找内容
	|----- NoteEdit.java				界面 修改笔记
	|----- NoteNew.java					界面 创建笔记
	|----- NoteRead.java				界面 笔记读取
	|----- OnlineRead.java				界面 在线阅读
	|----- ReadBook.java				界面 阅读图书
	|----- RecentlyRead.java			界面 最近阅读
	|----- SearchSDcard.java			界面 本地文件加入书架
	|----- WelcomeReader.java			界面 欢迎页
	|----- WriteBook.java				界面 写书
	|----- /net/blogjava/mobile/widget/	
	|----- FileBrowser.java				文件浏览自定义控件
	|----- OnFileBrowserListener.java	文件浏览监听接口
	|----- /sf/hmg/turntest/
	|----- BookPageFactory.java			阅读样式配置工具类
	|----- PageWidget.java				阅读样式配置自定义预览控件
	|----- turntest.java				阅读样式预览界面
	|----- /gen							开发工具资源id自动生成
	|----- /assets						原始文件使用目录
	|----- /bin							编译二进制目录
	|----- /res							资源文件目录
		/res/layout/background.xml				背景界面
		/res/layout/gridview_subitem.xml
		/res/layout/labellist_item.xml			书签子项界面
		/res/layout/labellist.xml				书签界面
		/res/layout/listview_rating.xml	
		/res/layout/localityread_check.xml		本地选择界面
		/res/layout/localityread_list.xml
		/res/layout/localityread_modification.xml
		/res/layout/localityread.xml			书库界面	
		/res/layout/localitysearch.xml
		/res/layout/main.xml					主界面
		/res/layout/memorandumread.xml			
		/res/layout/mybook.xml					界面我的图书
		/res/layout/note_addnew.xml				增加笔记
		/res/layout/note_check.xml
		/res/layout/note_edit.xml				笔记编辑
		/res/layout/note_read.xml				笔记读取
		/res/layout/onlineread.xml				在线阅读
		/res/layout/progressskip_item.xml
		/res/layout/readbook.xml				阅读图书
		/res/layout/recentlyread.xml			最近阅读
		/res/layout/simple_spinner_item001.xml
		/res/layout/subitem.xml
		/res/layout/textsettings.xml			配置样式
		/res/layout/textsize.xml				配置字体大小
		/res/layout/textspace.xml				配置行间距
		/res/layout/welcome.xml					欢迎页
		/res/layout/writebook.xml				写书
	|----- /AndroidManifest.xml			安卓主配置清单
	|----- /proguard.cfg				工程变量配置
	|----- /project.properties			工程版本配置


5、版本更新内容摘要

这是第一版V1.0，暂无更新。
