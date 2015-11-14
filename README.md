# PigRPG
幻猪的RPG插件FOR BUKKIT
V0.1

帮助：
插件名称: 幻猪RPG插件(PigRPG)
版本: 0.1
作者: 幻猪(Mg_P)
功能
	玩家右键菜单
		说明:潜行+右键其他玩家打开[右键菜单]
		内容(箱子)
			-玩家[在线信息]
				在线信息
					-名称
					-位置
					-游戏等级
					-是否OP
					-是否允许飞行
			-添加玩家好友/已是玩家好友(另见[好友系统])
			-交易申请(另见[交易系统-玩家交易]
	好友系统
		说明:使用命令打开玩家[好友列表]
		相关命令
			-pr friend list       打开玩家列表
			-pr friend accept     接受好友请求
			-pr friend deny       拒绝好友请求
			-pr friend del accept 接受好友删除请求
			-pr friend del deny   拒绝好友删除请求
		内容
			添加好友:另见[右键菜单]
			箱子
				好友列表
					-列举出该玩家的所有好友
					-好友头像显示[好友信息]
						好友信息
							-好友名称
							-是否在线
							-是否OP
							-是否白名(如果白名开启)
					-点击好友头像打开[好友菜单]
					-[好友菜单]大小随该玩家好友个数改变
				好友菜单
					-显示[好友信息]
					-发起[私聊](令见[聊天系统-聊天频道-私聊]
					-删除好友
	交易系统
		玩家交易
			说明:打开箱子界面，允许2个玩家上下分开放入物品，最终互换物品
			使用
				-通过玩家[右键菜单]发起交易
				-玩家接受交易后打开[玩家交易界面]
				-双方点击[确认交易]物品完成交易
				-再次点击物品以[更改交易]
				-[玩家交易界面]意外关闭时返还物品
			相关命令
				-pr trade accept 接受好友请求
				-pr trade deny   拒绝好友请求
			内容
				玩家交易界面
					-第1,2|4,5排分别提供给发起者|接受者放入交易物品
					-第3排2个物品用于[确认交易]
*		拍卖
*		固定交换(类似合成)
	传送系统
*		玩家传送
		Ess的warp传送界面(合并在[定点传送界面])
		定点传送界面
	聊天系统
		聊天频道
			说明:允许玩家设置所处聊天频道和接受某聊天频道内容
			频道内容:
				-全体
				-世界
*				-队伍
				-好友
				-私聊
			相关命令
				-pr chat setaccept 打开设置接受聊天频道列表
				-pr chat change    打开选择聊天频道列表
				-pr chat self      进入私聊频道
		聊天限制
			屏蔽关键字
				相关命令:pr chat mute ban [关键字]
			玩家禁言
				相关命令:pr chat mute [玩家] <true|false>
			全体禁言
				相关命令:pr chat muteall <true|false>
*	组队系统
*	阵营系统
*	悬赏系统
*	玩家系统
		装备系统
			装备栏
				说明
					-用于放置[装备]，[装备]只有放入[装备栏]才会生效(多数)
					-装备栏分不同[装备槽]，只有对应[装备类型]的[装备]才能放入该[装备槽]
						装备槽
							说明:具有不同类型，只有对应类型的[装备类型](或[子类型])才能放入
							内容
								-武器核心
								-头盔
								-护甲
								-护腿
								-战靴
								-首饰
								-项链
						
			装备
				说明:具有特殊效果的物品
				内容
					装备类型
						说明:区分装备
						内容
							-武器核心
							-武器(特)
								说明
									-特殊的装备类型
									-无法附加[装备效果]
									-其激发的[装备效果]由武器核心提供
							-头盔
							-护甲
							-护腿
							-战靴
							-首饰
							-项链
							-消耗品
								说明:该[装备类型]右键使用其[装备效果]，使用1次消失
										
								子类型
									说明
										-可在配置中设置上述类型的[子类型]
										-[子类型]可以放入其[父类型]的[装备槽]，[父类型]无法放入[子类型]
								父类型
										说明:[子类型]所继承的类型
					装备品质
						说明:无特殊效果，不同[装备品质]物品名称颜色不同
		技能
*	生物强化系统
*	野外地图系统
*	随机副本系统
!左侧有*的表示该功能及其子功能还未完成

所有权限
  pigrpg.*:
    description: PigRPG的所有权限
    default: op
    children:
      pigrpg.admin.*: true
  pigrpg.admin.*:
    description: PigRPG的管理员权限
    default: op
    children:
      pigrpg.default.*: true
      pigrpg.command.admin: true
  pigrpg.default.*:
    description: PigRPG的玩家基础权限
    default: op
    children:
      pigrpg.command.default: true
      pigrpg.trade.default: true
      pigrpg.teleport.default: true
  pigrpg.command.*:
    description: PigRPG的所有命令权限
    default: op
    children:
      pigrpg.command.admin: true
  pigrpg.command.admin:
    description: PigRPG的管理员命令权限
    default: op
    children:
      pigrpg.command.default: true
  pigrpg.command.default:
    description: PigRPG的所有权限
    default: op
    children:
      pigrpg.command.friend.default: true
      pigrpg.command.chat.default: true
  pigrpg.command.friend.default:
    description: friend命令的基础权限(list,accept,deny,del)
    default: op
  pigrpg.command.chat.default:
    description: chat命令的基础权限(setaccept,change,self)
    default: op
  pigrpg.trade.default:
    description: 交易的基础权限
    default: op
  pigrpg.teleport.default:
    description: 传送的基础权限
    default: op
    children:
      pigrpg.teleport.warp.*: true
  pigrpg.teleport.warp.*:
    description: 传送所有Warp的权限(子权限pigrpg.teleport.warp.[warp名称])
    default: op
    children:
      pigrpg.command.friend.default: true
