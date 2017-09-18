package com.junwu.mvplibrary.screen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * ===============================
 * 描    述：屏幕适配文件生成工具类
 * 默认设配屏幕：320, 360, 375, 384, 400, 411, 533, 640, 720, 760, 820
 * 自行根据项目情况，选择设配的目录生成对应适配文件
 * 建议：开发阶段生成一个模板设配文件，测试阶段用模板文件生成具体设配的所有文件
 * 根据模板文件生成其他设配文件方法具体参照main方法
 * 作    者：pjw
 * 创建日期：2017/7/24 13:44
 * ===============================
 */
public class ScreenUtils {

    private final static String WRAP = "\n";//换行符
    private final static String TITLE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + WRAP;//文件内容开头
    private final static String TEMPLATEF = "   <dimen name=\"sp{0}\">{1}sp</dimen>" + WRAP;//sp格式
    private final static String TEMPLATEP = "   <dimen name=\"dp{0}\">{1}dp</dimen>" + WRAP;//dp格式
    private final static String TEMPLATEPM = "   <dimen name=\"dpm{0}\">{1}dp</dimen>" + WRAP;//dp值为负值的格式
    private final static String TEMPLATE_DIR = "values-w{0}dp";//存放dimens文件的目录
    private final static String FILENAME = "screen_dimens.xml";//dimens文件名称

    /**
     * 写入文件
     *
     * @param file    文件
     * @param content 内容
     * @return 是否成功
     */
    private static boolean writeToFile(File file, String content) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(file));
            pw.print(content);
            pw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取内容
     *
     * @param file 读取的文件
     * @return 文件内容
     */
    private static String readFile(File file) {
        String content = "";
        try {
            Long fileLens = file.length();
            byte[] bytes = new byte[fileLens.intValue()];
            FileInputStream fs = new FileInputStream(file);
            fs.read(bytes);
            fs.close();
            content = new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 获取文件的绝对路径
     *
     * @param file 文件
     * @return 绝对路径
     */
    private static File getAbsolutePath(File file) {
        return new File(file.getAbsolutePath(), FILENAME);
    }

    /**
     * 获取文件
     *
     * @param w values-w320dp
     * @return 文件
     */
    private static File getFile(int w) {
        return getFile("./res", w);
    }

    /**
     * 获取文件
     *
     * @param dir 路径
     * @param w   values-w320dp
     * @return 文件
     */
    private static File getFile(String dir, int w) {
        return new File(dir + File.separator + TEMPLATE_DIR.replace("{0}", w + ""));
    }

    /**
     * 生成默认xml
     *
     * @return 默认xml
     */
    private static String generateDefaultXml() {
        StringBuilder output = new StringBuilder();
        output.append(TITLE);
        output.append("<resources>\n");
        //dp值
        for (int i = -20; i <= 50; i++) {
            if (i == 0) {
                continue;
            }
            if (i < 0) {
                output.append(TEMPLATEPM.replace("{0}", (i * -1) + "").replace("{1}", i + ""));
            } else {
                output.append(TEMPLATEP.replace("{0}", i + "").replace("{1}", i + ""));
            }
        }
        for (int i = 55; i < 100; i += 5) {
            output.append(TEMPLATEP.replace("{0}", i + "").replace("{1}", i + ""));
        }
        //sp值
        for (float i = 10; i <= 38; i += 0.5f) {
            output.append(TEMPLATEF.replace("{0}", i + "").replace("{1}", i + ""));
        }
        for (int i = 40; i < 60; i += 4) {
            output.append(TEMPLATEF.replace("{0}", i + "").replace("{1}", i + ""));
        }
        output.append("</resources>");
        return output.toString();
    }

    /**
     * 更具模板生成对应dp文件下的内容
     *
     * @param benchmarkW 模板宽度
     * @param toWidthDP  转换后的宽度
     * @param dimens     需要转换的数组
     * @return 转换后的内容
     */
    private static String generateXml(int benchmarkW, int toWidthDP, String[] dimens) {
        StringBuilder sbXml = new StringBuilder();
        StringBuilder sbTemp = new StringBuilder();
        float proportion = toWidthDP * 1.0f / benchmarkW;//转换的倍数
        int[] indexs = new int[2];
        String strTemp = null;
        float temp;
        for (int i = 0, size = dimens.length; i < size; i++) {
            strTemp = dimens[i];
            getDimenIndexs(strTemp, indexs);
            if (indexs[0] >= 0 && indexs[1] >= 0) {
                sbTemp.setLength(0);
                sbTemp.append(strTemp);
                //四舍五入后保留两位小数
                temp = (int) ((getDimenValue(strTemp, indexs) * proportion + 0.5f) * 100);
                temp = temp / 100.0f;//保留两位小数
                sbTemp.replace(indexs[0], indexs[1], temp + "");
                sbXml.append(sbTemp.toString());
            } else {
                sbXml.append(strTemp);
            }
            sbXml.append(WRAP);
        }
        return sbXml.toString();
    }

    /**
     * 获取下标
     *
     * @param str    从str获取下标
     * @param indexs 下标存放地址,可以为空，为空后自动创建数组并返回
     * @return indexs
     */
    private static int[] getDimenIndexs(String str, int[] indexs) {
        if (indexs == null || indexs.length < 2) {
            indexs = new int[2];
        }
        indexs[0] = str.indexOf("\">");
        indexs[1] = str.indexOf("dp<");
        if (indexs[0] >= 0) {
            indexs[0] += 2;
        }
        if (indexs[1] < 0) {
            indexs[1] = str.indexOf("sp<");
        }
        return indexs;
    }

    /**
     * 获取file的文件尺寸
     *
     * @param file 文件
     * @return 尺寸
     */
    private static int getBenchmark(File file) {
        String dp = file.getName().replace("values-w", "").replace("dp", "");
        return Integer.valueOf(dp);
    }

    /**
     * 获取Dimen的值
     *
     * @param str    从str中得到值
     * @param indexs 获取值的下标
     * @return Dimen的值
     */
    private static float getDimenValue(String str, int[] indexs) {
        str = str.substring(indexs[0], indexs[1]);
        return Float.parseFloat(str);
    }

    /**
     * 移除默认模板
     *
     * @param benchmarkW 模板
     * @param widthDps   需要创建的设配文件
     * @return 真正需要创建的设配文件
     */
    private static int[] removeDefault(int benchmarkW, int[] widthDps) {
        int index = -1, size = widthDps.length;
        for (int i = 0; i < size; i++) {
            if (widthDps[i] == benchmarkW) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return widthDps;
        }
        int[] newWidthDps = new int[size - 1];
        for (int i = 0; i < size; i++) {
            if (i != index) {
                if (i < index) {
                    newWidthDps[i] = widthDps[i];
                } else {
                    newWidthDps[i - 1] = widthDps[i];
                }
            }
        }
        return newWidthDps;
    }

    /**
     * 创建以benchmarkW为模板尺寸的内容
     *
     * @param benchmarkW 模板尺寸
     * @param dir        保存路径
     * @param benchmark  模板路径
     * @param widthDps   创建的目录
     */
    private static void generate(int benchmarkW, String dir, File benchmark, int... widthDps) {
        String context;
        boolean isTrue;
        //模板的绝对路径地址
        File benchmarkFile = getAbsolutePath(benchmark);
        if (!benchmarkFile.exists()) {//如果文件不存在就创建默认模板
            benchmarkW = widthDps[0];
            File fileDir = getFile(dir, benchmarkW);
            fileDir.mkdirs();
            //创建默认模板内容
            context = generateDefaultXml();
            //保存默认模板
            isTrue = writeToFile(getAbsolutePath(fileDir), context);
            System.out.println("默认模板不存在，默认创建" + fileDir.getName() + "模板文件，状态值为：" + isTrue);
            if (!isTrue) {
                return;
            }
        } else {
            //读取模板文件
            context = readFile(benchmarkFile);
        }
        if (benchmarkW <= 0) {
            return;
        }
        //移除模板文件
        widthDps = removeDefault(benchmarkW, widthDps);
        //拆分
        String[] dimens = context.split("\n");
        for (int widthDp : widthDps) {
            File fileDir = getFile(dir, widthDp);
            fileDir.mkdirs();//创建values-w320dp文件
            //待写入文件
            File xmlFile = getAbsolutePath(fileDir);
            //写入文件
            isTrue = writeToFile(xmlFile, generateXml(benchmarkW, widthDp, dimens));
            System.out.println("写入：" + fileDir.getName() + "文件，状态值为：" + isTrue);
        }
    }

    /**
     * 创建默认目录下的尺寸文件
     */
    private static void generate() {
        generate(320, 360, 375, 384, 400, 411, 533, 640, 720, 760, 820);
    }

    /**
     * 创建以values-w320dp为模板的内容
     *
     * @param widthDps 创建的目录
     */
    private static void generate(int... widthDps) {
        File fileDir = getFile(320);
        generate(fileDir, widthDps);
    }

    /**
     * 创建以benchmark为模板的内容，保存路径为./res
     *
     * @param benchmark 模板路径
     * @param widthDps  创建的目录
     */
    private static void generate(File benchmark, int... widthDps) {
        generate("./res", benchmark, widthDps);
    }

    /**
     * 创建dir路径下的内容
     *
     * @param dir       路径
     * @param benchmark 模板路径
     * @param widthDps  创建的目录
     */
    private static void generate(String dir, File benchmark, int... widthDps) {
        //获取模板文件尺寸
        int benchmarkW = getBenchmark(benchmark);
        generate(benchmarkW, dir, benchmark, widthDps);
    }

    public static void main(String[] arg) {
//        //生成对应设配文件，如果没有模板则以第一个尺寸生成设配文件
//        generate(320, 360, 375, 384, 400, 411, 533, 640, 720, 760, 820);
        //根据320模板文件，生成其他设配文件
        generate(getFile(375), 320, 360, 375, 384, 400, 411, 533, 640, 720, 820);
    }

}
