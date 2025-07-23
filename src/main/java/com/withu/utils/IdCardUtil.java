package com.withu.utils;

import com.aliyuncs.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 身份证验证规则：
 * 第十八位数字（校验码）的计算方法为：
 * 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
 * 2.将这17位数字和系数相乘的结果相加与11进行相除。
 * 3.余数0 1 2 3 4 5 6 7 8 9 10这11个数字，其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2。
 * 4.例如 余数为 0 , 则身份证最后一位就是1
 *       余数为 2 , 则身份证最后一位就是罗马数字X
 *       余数为 10 , 则身份证最后一位就是2
 * @ClassName: IdCardUtil
 * @date: 2019/10/24 17:10
*/
@Slf4j
public class IdCardUtil {
    /**
     * 校验是否为身份证号码
     * @param IdCardNo
     * @return
     */
    public static  boolean isIdCardNo(String IdCardNo){
 
        boolean IdCardflag =  false;
 
        try {
 
            if (!StringUtils.isEmpty(IdCardNo) && IdCardNo.length() == 18){
                // 1.将身份证号码前面的17位数分别乘以不同的系数。
                int[] coefficientArr = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
                int sum = 0;
                for (int i = 0; i < coefficientArr.length; i++) {
                    // Character.digit 在指定的基数返回字符ch的数值。如果基数是不在范围内MIN_RADIX≤基数≤MAX_RADIX或如果该值的通道是不是一个有效的数字在指定的基数-1，则返回。
                    // ch - the character to be converted(要转换的字符)
                    // ch - int类型，是字符的ASCII码，数字的ASCII码是48-57
                    // radix - the radix(基数) ----也就是进制数
                    sum += Character.digit(IdCardNo.charAt(i), 10) * coefficientArr[i];
                }
 
                // 余数数组
                int[] remainderArr = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
 
                // 身份证号码第18位数组
                int[] lastArr = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
                String matchDigit = "";
                for (int i = 0; i < remainderArr.length; i++) {
                    int j = remainderArr[i];
                    if (j == sum % 11) {
                        matchDigit = String.valueOf(lastArr[i]);
                        if (lastArr[i] > 57) {
                            matchDigit = String.valueOf((char) lastArr[i]);
                        }
                    }
                }
 
                if (matchDigit.equals(IdCardNo.substring(IdCardNo.length() - 1))) {
                    IdCardflag =  true;
                }
            }
 
        }catch (Exception e) {
 
            log.error(String.format("校验身份证出现异常%s",e));
        }
 
        return  IdCardflag;
 
    }
 
    /**
     * 身份证号码脱敏 只脱敏月日四位
     */
    public  static  String getNewIdCardNo(String IdCardNo){
 
        String IdCardNoTemmp = IdCardNo;
        if (isIdCardNo(IdCardNo)){
            StringBuilder sb = new StringBuilder(IdCardNo);
            sb.replace(10, 14, "****");
            IdCardNoTemmp = sb.toString();
        }
 
        return IdCardNoTemmp;
    }
}