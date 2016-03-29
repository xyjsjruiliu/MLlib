/**
 * 
 */
package com.xy.lr.java.simhash;

import com.xy.lr.java.math.FeelTheBase;
import com.xy.lr.java.math.MurmurHash;

import java.util.List;

/**
 * 
 * @author xylr 2016-01-05
 *
 */
public class Simhash {

	private static IWordSeg wordSeg;

	public Simhash(IWordSeg wordSeg) {
		this.wordSeg = wordSeg;
	}

//	public int hammingDistance(int hash1, int hash2) {
//		int i = hash1 ^ hash2;
//		i = i - ((i >>> 1) & 0x55555555);
//		i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
//		i = (i + (i >>> 4)) & 0x0f0f0f0f;
//		i = i + (i >>> 8);
//		i = i + (i >>> 16);
//		return i & 0x3f;
//	}

//	public int hammingDistance(long hash1, long hash2) {
//		long i = hash1 ^ hash2;
//		i = i - ((i >>> 1) & 0x5555555555555555L);
//		i = (i & 0x3333333333333333L) + ((i >>> 2) & 0x3333333333333333L);
//		i = (i + (i >>> 4)) & 0x0f0f0f0f0f0f0f0fL;
//		i = i + (i >>> 8);
//		i = i + (i >>> 16);
//		i = i + (i >>> 32);
//		return (int) i & 0x7f;
//	}

	/**
	 * 计算两个Simhash值的距离
	 * @param hash1 hash1
	 * @param hash2 hash2
     * @return 距离
     */
	public int hammingDistance(String hash1, String hash2) {
		if(hash1.length() != 64 || hash2.length() != 64) {
			return -1;//表示计算异常
		}else {
			int count = 0;
			for(int i = 0; i < 64; i++) {
				if(hash1.charAt(i) != hash2.charAt(i))
					count++;
			}
			return count;
		}
	}

	/**
	 * simhash
	 * @param doc 文章
	 * @return SimHash值
     */
//	public String simHash64(String doc) {
//		long hash = simhash64(doc);
//		return FeelTheBase.longToBinary(hash, 64);
//	}

	/**
	 * simhash值
	 * @param doc 文章分词之后的结果
	 * @return simhash值
     */
	public String simhash64(String doc) {
		int bitLen = 64;
		List<String> tokens = wordSeg.tokens(doc);
		int[] bits = new int[bitLen];

		for(String token : tokens) {
			//首先对每个单词做一遍Hash
			String murmurHash = FeelTheBase.longToBinary(MurmurHash.hash64(token), 64);

			for(int i = 0; i < bitLen; i++) {//
				int temp = 0;
				if(murmurHash.charAt(i) == 49) temp = 1;
				else temp = -1;
				bits[i] += temp;
			}
		}

		String result = "";
		for(int bit : bits)
			if(bit > 0) result += 1;
			else result += 0;

		return result;
	}
/*
	public static long simhash64(String doc) {
		int bitLen = 64;
		int[] bits = new int[bitLen];
		List<String> tokens = wordSeg.tokens(doc);
		for (String t : tokens) {
			long v = MurmurHash.hash64(t);
			for (int i = bitLen; i >= 1; --i) {
				if (((v >> (bitLen - i)) & 1) == 1)
					++bits[i - 1];
				else
					--bits[i - 1];
			}
		}
		long hash = 0x0000000000000000;
		long one = 0x0000000000000001;
		for (int i = bitLen; i >= 1; --i) {
			if (bits[i - 1] > 1) {
				hash |= one;
			}
			one = one << 1;
		}
		return hash;
	}
*/
//	public long simhash32(String doc) {
//		int bitLen = 32;
//		int[] bits = new int[bitLen];
//		List<String> tokens = wordSeg.tokens(doc);
//		for (String t : tokens) {
//			int v = MurmurHash.hash32(t);
//			for (int i = bitLen; i >= 1; --i) {
//				if (((v >> (bitLen - i)) & 1) == 1)
//					++bits[i - 1];
//				else
//					--bits[i - 1];
//			}
//		}
//		int hash = 0x00000000;
//		int one = 0x00000001;
//		for (int i = bitLen; i >= 1; --i) {
//			if (bits[i - 1] > 1) {
//				hash |= one;
//			}
//			one = one << 1;
//		}
//		return hash;
//	}

	public static void main(String[] args) {
		Simhash simhash = new Simhash(new ChineseInfoWordSeg());
//		int a = simhash.hammingDistance("1111111111111111111111111111111111111111111111111111111111111111",
//				"0000000000000000000000000001111100000000000000000000000000000000");
//
//		String s = simhash.simhash64("我 是 我司爱上 阿斯顿");

//		System.out.println(s);
//		System.out.println(a);

		String c = simhash.simhash64("您好 上 昨日 举行 上 国务院 政策 例行 吹风会 上 住房 和 城乡 建设部 副 部长 齐骥 介绍 说 截至 去年底 我国 启动 改造 各类 棚户区 约 2100 万 套 到 现在 到 2020 年 还有 约 2000 万 套 的 棚户区 需要 改造 实施 十二五 规划 4年 来 我国 已 改造 各类 棚户区 1580 万 户 初步 推算 有 4000 万 万 群众 摆脱 棚户区 住房 的 困境 住 上 生活 设施 相对 完备 的 楼房 齐骥 介绍 说 我国 要 开工 筹集 保障 性 安居工程 700 万 套 以上 其中 七成 甚至 更多 一点 棚户区 改造 住房 2015 年 要 基本 建成 480 万 套 保障 性 安居工程 齐 骥说 十二五 规划 提出 十二五 时期 开工 建设 各类 保障 性 住房 和 棚户区 改造 安置 住房 3600 万 套 的 目标 至 2011 年 至 2014 年 即 十二五 前 4年 已 累计 开工 建设 各类 保障 性 住房 和 棚户区 改造 安置 住房 3200 万 套 基本 建成 2000 万 套 以上 齐骥 还 介绍 说 对于 农村 困难 群众 居住 的 危房 也 给予 定的 支持 政策 和 补助 资金 推动 实施 农村 危房 改造 2008 年 至今 我国 累计 改造 近 1600 万 套 农村 危房 2015 年 还 计划 再 改造 300 万 套 以上 扫 扫 用 手机 看 新闻 用 微信 扫描 还 可以 分享 至 好友 和 朋友 圈"+                "");
		String d = simhash.simhash64("2月份 以来 楼市 进 一步 暖 呈现 量价 齐升 的 局面 国家 统计局 昨日 公布 数 据 显示 70 个 大中城市 中 无论是 新房 还是 二手 房 环 比 价格 上涨 的 城市 数量 均超 九成 且 环比 涨幅 继续 扩大 新房 环比 下降 的 城市 仅有 一个 房价 持续 上涨 表明 调控 力度 须进 一步 加强 分析 人士 认为 初 本月 初 “国 五条 细 则 出台 后 各地 也 将 前 本月底 前 出台 具体 细则 有助于 抑制 涨势 数 据 显示 70 个 大中城市 中 新房 二 手房 价格 环比 上涨 的 城市 都有 66 个 比 分别 多 出 11 个 和 15 个 值得 注意 的 新房 环比 价格 下降 的 城市 仅有 温州 一个 此外 从 房价 涨幅 来 看 新房 二 手房 的 房价 环比 涨幅 均 继续 扩大 数 据 显示 新房 价格 环比 上涨 的 66 个 城市 中 62 个 城市 涨幅 继续 扩大 11 个 城市 涨幅 比 增加 1个 百分点 此 伟业 我爱我家 集团 副 总裁 胡 景晖 分析 认为 房价 上涨 主要 有 两 方面 原因 政策 出现 暂时 的 真 空期 加上 政策 不 确定性 购房 者 担心 资格 受到 限制 或者 购房 成本 增加 加上 买 涨 不买 跌 的 心理 作用 之下 购房 需求 旺盛 二 开发 企业 几乎 没有 销售 压力 新房 推盘 动力 不足 二手 房源 经历 长 时间 的 消化 之后 总体 房源 较为 紧张 的 较为 严峻 的 供需 形势 之下 随着 近期 多个 城市 的 楼市 成交量 持续 走高 房价 也 出现 继续 涨 的 局面 胡 景晖 说 国务院 3月 1日 出台 旨在 抑制 楼市 过热 的 “国 五条 细则 调控 政策 执行 方面 的 严厉 程度 进 一步 升级 其中 提到 有 出售 有 有 住房 按 规定 应 征收 的 个人所得税 通过 税收 征管 房屋 登记 历史 信息 能 核实 房屋 原值 的 应 依法 严格 按 转让 所得 的 20 % 计征 这 规定 引发 购房 者 入 市意 愿 激增 多地 二手 房 成交量 短 时间 内 大增 根据 北京市 的 建委 网站 公布 的 最新 数据 显示 今年 3月 1日 至今 北京 全市 二手 住宅 网签 总量 已达 2.4 万 套 创下 近 34 个 月 来 的 月度 成交量 新高 而 “国 五条 细 则 出台 的 第一 周 深圳市 二手 房 市场 应激 出现 交易量 剧增 周 成交量 环比 涨幅 近八成 与 一线 城市 井 喷 式 的 交易量 相 对应 的 北京 上海 广州 深圳 一线 城市 也是 此 轮 房价 快速 反弹 的 领衔 城市 以 北京 为 例 其 去年 12 月 至 今年 2月 的 新房 价格 环比 涨幅 逐渐 扩大 涨幅 依次 为 0.8 % 1.6 % 2.4 % 更 继续 扩大 至 3.1 % 成为 当月 环比 涨幅 最高 的 城市 其他 城市 上海 环比 上涨 2.3 % 广州 环比 上涨 3.1 % 深圳 上涨 2.2 % 从 二手 房 价格 来 看 北京 环比 上涨 2.2 % 上海 环比 上涨 1.6 % 广州 环比 上涨 1.7 % 深圳 环比 上涨 1.4 % 中原 地产 市场 研究 部 总监 张 大伟 分析 认为 一线 城市 房价 上涨 幅度 远远 超过 其他 二 三线 城市 主要 是因为 限购 政策 执行 近 两年 后 积压 的 需求 加速 释放 特别 之前 的 调控 政策 力度 有限 使得 购房 者 出现 上涨 预期 加速 入市 同时 一线 城市 的 信贷 相比 其他 城市 更加 宽松 基本 执行 的 均为 首套 房 贷款 85 折 70 个 城市 房价 指 数环 比 平均 大涨 1 1 08 % 这个 涨幅 已 接近 2009 年 四 季度 和 2010 年 季度 的 疯涨 水平 房价 加速 上涨 的 力度 之 大 让人 惊诧 国 五条 的 出台 非常 必要 上海 易居 房地产 研究院 副 院长 杨 红旭 说 稍 早住 建部 副 部长 齐骥 两会 期间 答 记者 提问 时 表示 各地 的 房地产 调控 目标 需 地方 政府 制定 并 发布 预计 3月 底 调控 细则 将 出台 分析 人士 认为 “国 五条 关键 还是 看 地方 政府 相关 的 实施 细则 房价 上涨 的 趋势 会 随着 政策 实施 而 有所 缓和 但 没有 下调 的 可能性 为 抑制 房价 涨势 还应 加大 保 有 环节 的 税收 力度 并 增加 土地 供应");

		System.out.println(simhash.hammingDistance(c, d));
	}
}
