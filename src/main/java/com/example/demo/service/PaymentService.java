package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.stereotype.Service;

import com.example.demo.config.PaymentVNPAYConfig;
import com.example.demo.mservice.config.Environment;
import com.example.demo.mservice.enums.RequestType;
import com.example.demo.mservice.models.PaymentResponse;
import com.example.demo.mservice.processor.CreateOrderMoMo;
import com.example.demo.mservice.shared.utils.LogUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentService {
	
	
	
    public Map<String, String> paymentWithMomo(long amount, String id) {
        LogUtils.init();
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());
        String orderInfo = "Pay With MoMo";
        String returnURL = "https://pantherscinema.vercel.app/";
        String notifyURL = "https://google.com.vn";

        Environment environment = Environment.selectEnv("dev");
        PaymentResponse captureWalletMoMoResponse = null;
        try {
            captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId,
                    Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM, null);
        } catch (Exception e) {
            log.error("Error while processing MoMo payment", e);
        }

        Map<String, String> response = new HashMap<>();
        if (captureWalletMoMoResponse != null && captureWalletMoMoResponse.getPayUrl() != null) {
            log.info("Pay URL: " + captureWalletMoMoResponse.getPayUrl());
            response.put("status", "OK");
            response.put("message", "Successfully");
            response.put("paymentUrl", captureWalletMoMoResponse.getPayUrl());
        } else {
            response.put("status", "FAILED");
            response.put("message", "Failed to create MoMo order");
        }
        return response;
    }

    public Map<String, String> paymentWithVnPay(long amount, String id, HttpServletRequest request) throws UnsupportedEncodingException {
        log.info("Starting VNPay payment for id: {}, amount: {}", id, amount);

        amount = amount * 100;
        String vnp_Version = "2.1.0"; // Phiên bản API VNPay
        String vnp_Command = "pay";   // Lệnh thanh toán
        String orderType = "other";   // Loại đơn hàng (ở đây là "other")
        String bankCode = "NCB";      // Mã ngân hàng mặc định (test NCB)
        String vnp_TxnRef = PaymentVNPAYConfig.getRandomNumber(8); // Số tham chiếu đơn hàng (ngẫu nhiên)
        String vnp_TmnCode = PaymentVNPAYConfig.vnp_TmnCode;

        // Lấy IP client từ query parameter (thay vì từ request header)
        String clientIp = request.getParameter("ip");
        if (clientIp == null || clientIp.isEmpty()) {
            log.error("Client IP không hợp lệ.");
            clientIp = "Unknown"; // Thay thế bằng một giá trị mặc định nếu không có IP
        }

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_ReturnUrl", PaymentVNPAYConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", clientIp); // Thêm IP client vào vnp_Params

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII)).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String vnp_SecureHash = PaymentVNPAYConfig.hmacSHA512(PaymentVNPAYConfig.secretKey, hashData.toString());
        String paymentUrl = PaymentVNPAYConfig.vnp_PayUrl + "?" + query + "&vnp_SecureHash=" + vnp_SecureHash;

        log.info("Generated VNPay payment URL: {}", paymentUrl);

        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Successfully generated VNPay payment URL");
        response.put("paymentUrl", paymentUrl);

        return response;
    }

}
