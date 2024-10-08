<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Giỏ hàng</title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/styles.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" />

<style>
body {
	background-color: #f5f5f5;
}

.cart-header {
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 20px;
	background-color: #fff;
	border-bottom: 1px solid #ddd;
}

.cart-item {
	background-color: #fff;
	margin-bottom: 10px;
	padding: 20px;
	border: 1px solid #ddd;
	border-radius: 10px;
	display: flex;
	align-items: center;
}

.cart-item img {
	max-width: 100px;
	border-radius: 10px;
}

.cart-item-info {
	display: flex;
	align-items: center;
}

.cart-item-info h5 {
	margin: 0 20px;
}

.cart-item-actions {
	display: flex;
	align-items: center;
}

.cart-item-actions button {
	border: 1px solid #ddd;
	border-radius: 5px;
	background-color: #fff;
	width: 30px;
	height: 30px;
}

.cart-summary {
	background-color: #fff;
	padding: 20px;
	border: 1px solid #ddd;
	border-radius: 10px;
	text-align: center;
}

.btn-buy {
	padding: 10px 20px;
	border-radius: 5px;
	width: 100%;
	margin-top: 20px;
}

.cart-item-actions {
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 10px;
	border: 1px solid #ccc;
	border-radius: 5px;
	background-color: #f9f9f9;
	margin: auto;
}

.cart-item-actions p {
	margin: 0;
}

.cart-item-actions button {
	background-color: transparent;
	border: none;
	cursor: pointer;
	padding: 5px;
}

.cart-item-actions .text-danger {
	color: red;
	font-weight: bold;
}

.cart-item-actions del {
	color: #999;
	text-decoration: line-through;
}

.cart-item-actions span {
	margin: 0 5px;
}

.modal-dialog {
	margin: 13.75rem auto;
}

#purchaseModal > div > div > div.modal-body > p {
	margin-left: 20px;
    margin-top: 10px;
}

#purchaseModalLabel {
	margin: 0 0 0 42%;
}

.toast {
    position: fixed; /* Đảm bảo rằng toast sẽ nằm cố định trên màn hình */
    bottom: 20px; /* Khoảng cách từ đáy của cửa sổ trình duyệt */
    right: 20px; /* Khoảng cách từ bên phải của cửa sổ trình duyệt */
    z-index: 1050;
}

</style>
</head>
<body>
	<%@include file="layout/header.jsp"%>

	<div class="container cart-container" style="margin-top: 80px;">
		<!-- Cart Header -->
		<div class="cart-header">
			<button class="btn btn-light">
				<a href="/" style="color: black;"><i class="bi bi-arrow-left"></i></a>
			</button>
			<h2 style="margin-right: 37%;">Giỏ hàng của bạn</h2>
		</div>
		<br>

		<!-- Cart Items -->
		<table class="table">
			<thead>
				<tr class="text-center" style="padding: 20px;">
					<th></th>
					<th class="col-5">Thông tin tài khoản</th>
					<th class="col-3">Tên game</th>
					<th class="col-2" style="padding-right: 50px !important;">Giá</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${cartItems}" var="item">
					<tr class="text-center">
						<td><input type="checkbox" data-price="${item.gameAccount.price}"></td>
						<td class="col-5">${item.gameAccount.accountName}</td>
						<td class="col-3">${item.gameAccount.gameName}</td>
						<td class="col-2" style="padding-right: 50px !important;">${item.gameAccount.price}</td>
						<td><a class="btn btn-outline-danger bi bi-trash"
							href="${pageContext.request.contextPath}/removeFromCart?cartItemId=${item.cartItemId}"></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>


		<div class="cart-item">
			<p>Bảo vệ toàn diện với Bảo hành mở rộng</p>
		</div>

		<!-- Cart Summary -->
		<div class="cart-summary">
			<h5>Tổng tiền</h5>
			<p>
				<span class="text-danger" id="tongtienThanhtoan">0₫</span>
			</p>
			<button class="btn btn-danger btn-buy" id="buyButton" onclick="connectWallet()" disabled>Mua ngay</button>
		</div>
		
		<!-- Modal -->
		<div class="modal fade" id="purchaseModal" tabindex="-1" aria-labelledby="purchaseModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="purchaseModalLabel">Thông tin mua hàng</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <p><strong>Tên tài khoản:</strong> <span id="modalAccountNames"></span></p>
		        <p><strong>Tên game:</strong> <span id="modalGameNames"></span></p>
		        <p><strong>Tổng tiền:</strong> <span id="modalTotalPrice"></span></p>
		        <p><strong>Public key:</strong> <span id="publicKey"></span></p> <!-- Hiển thị public key -->
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
		        <button type="button" class="btn btn-primary" id="confirmPurchaseButton">Xác nhận mua</button>
		      </div>
		    </div>
		  </div>
		</div>

		
	</div>
	
	<!-- Toast container -->
	<div aria-live="polite" aria-atomic="true">
	    <div id="loginToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-autohide="false">
	        <div class="toast-header">
	            <strong class="mr-auto">Thông báo</strong>
	            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
	                <span aria-hidden="true">&times;</span>
	            </button>
	        </div>
	        <div class="toast-body">
	            <div id="status_text"></div>
	        </div>
	    </div>
	</div>

	<%@include file="layout/footer.jsp"%>
	

	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script src="https://unpkg.com/@solana/web3.js@latest/lib/index.iife.js"></script>
	<script src="https://bundle.run/buffer@6.0.3"></script>
	<script type="text/javascript">
        window.Buffer = window.Buffer ?? buffer.Buffer;
    </script>
	
	<script>
    $(document).ready(function() {
    	let statusText = $('#status_text').text().trim();
        if (statusText.length > 0) {
            $('#loginToast').toast('show'); // Hiển thị toast

            // Ẩn toast sau 5 giây
            setTimeout(function() {
                $('#loginToast').toast('hide');
            }, 5000);
        }

        $('input[type="checkbox"]').change(function() {
            let total = 0;

            // Tính tổng giá trị của các checkbox được chọn
            $('input[type="checkbox"]:checked').each(function() {
                total += parseFloat($(this).data('price'));
            });

            // Cập nhật tổng tiền vào span
            $('#tongtienThanhtoan').text(total.toLocaleString('vi-VN') + '₫');

            // Kích hoạt hoặc vô hiệu hóa nút "Mua ngay"
            if (total > 0) {
                $('#buyButton').prop('disabled', false);
            } else {
                $('#buyButton').prop('disabled', true);
            }
        });
        
     	// Bắt sự kiện click vào nút "Mua ngay"
        $('#buyButton').click(function() {
            let accountNames = [];
            let gameNames = [];
            let totalPrice = 0;

            // Lấy dữ liệu của các checkbox đã chọn
            $('input[type="checkbox"]:checked').each(function() {
                let row = $(this).closest('tr'); // Lấy hàng tương ứng với checkbox
                let accountName = row.find('td:nth-child(2)').text(); // Tên tài khoản
                let gameName = row.find('td:nth-child(3)').text(); // Tên game
                let priceText = row.find('td:nth-child(4)').text(); // Giá dưới dạng chuỗi
				
                let price = parseFloat(priceText.replace(/[^\d.]/g, ''));
                // Thêm vào mảng
                accountNames.push(accountName);
                gameNames.push(gameName);

                // Cộng tổng tiền
                totalPrice += price;
            });
            
            let username = '${sessionScope.loggedInUser.username}'; // Tên người dùng
            let email = '${sessionScope.loggedInUser.email}'; // Email người dùng

            // Hiển thị thông tin trong modal
            $('#modalUsername').text(username);
        	$('#modalEmail').text(email);
            $('#modalAccountNames').text(accountNames.join(', '));
            $('#modalGameNames').text(gameNames.join(', '));
            $('#modalTotalPrice').text(totalPrice + ' SOL');

            // Hiển thị modal
            $('#purchaseModal').modal('show');
        });
     	
     	// Bắt sự kiện click vào nút "Xác nhận mua" trong modal
        $('#confirmPurchaseButton').click(function() {
            sendSOLtoWallet(); // Gọi hàm gửi SOL
        });
    });
    
    var wallet;
    const receiverAddress = "96Yff5j7UStX1seAJ3HvU3iJrdABXGbihPT58N2yhedz";
    
 	// Kết nối ví Solana
    function connectWallet() {
        (async () => {
            try {
                wallet = await window.solana.connect(); // Kết nối ví Solana
                console.log(wallet.publicKey.toString());
                document.getElementById('publicKey').innerText = wallet.publicKey.toString(); // Hiển thị public key trong modal
                $('#status_text').text("Kết nối ví thành công!"); // Hiển thị thông báo kết nối ví thành công
                $('#loginToast').toast('show');

                // Ẩn toast sau 5 giây
                setTimeout(function() {
                    $('#loginToast').toast('hide');
                }, 5000);
            } catch (err) {
                console.log(err);
                $('#status_text').text("Kết nối ví thất bại!"); // Hiển thị thông báo kết nối ví thất bại
                $('#loginToast').toast('show');

                // Ẩn toast sau 5 giây
                setTimeout(function() {
                    $('#loginToast').toast('hide');
                }, 5000);
            }
        })();
    }

 	// Gửi SOL
    async function sendSOLtoWallet() {
        const totalPriceElement = document.getElementById("modalTotalPrice");

        // Kiểm tra xem phần tử có tồn tại không
        if (!totalPriceElement) {
            console.error("Phần tử với ID 'modalTotalPrice' không tồn tại.");
            return;
        }

        // Lấy giá trị tổng tiền từ phần tử modal và chuyển đổi thành số
        const totalPriceText = totalPriceElement.innerText;
        const quantity = parseFloat(totalPriceText.replace(/[^\d.]/g, ''));

        if (isNaN(quantity) || quantity <= 0) {
            $('#status_text').text("Số lượng SOL phải lớn hơn 0 và phải hợp lệ!");
            $('#loginToast').toast('show');

            setTimeout(function() {
                $('#loginToast').toast('hide');
            }, 5000);
            return;
        }

        $('#status_text').text("Đang gửi " + quantity + " SOL đến địa chỉ " + receiverAddress);
        $('#loginToast').toast('show');

        signInTransactionAndSendMoney(receiverAddress, quantity)
            .then(() => {
                $('#status_text').text("Gửi SOL thành công!");
            })
            .catch(error => {
                $('#status_text').text("Gửi SOL thất bại!");
            })
            .finally(() => {
                setTimeout(function() {
                    $('#loginToast').toast('hide');
                }, 5000);
            });
    }

    async function signInTransactionAndSendMoney(destPubkeyStr, quantity) {
        (async () => {
            const network = "https://api.devnet.solana.com";
            const connection = new solanaWeb3.Connection(network);
            const transaction = new solanaWeb3.Transaction();
        
            try {
                const N_lamports = quantity * solanaWeb3.LAMPORTS_PER_SOL;
                console.log(N_lamports);
          
                console.log("starting sendMoney");
                const destPubkey = new solanaWeb3.PublicKey(destPubkeyStr);

                const instruction = solanaWeb3.SystemProgram.transfer({
                    fromPubkey: wallet.publicKey,
                    toPubkey: destPubkey,
                    lamports: N_lamports,
                });
                let trans = await setWalletTransaction(instruction, connection);
          
                let signature = await signAndSendTransaction(
                    wallet,
                    trans,
                    connection
                );
                console.log("Transaction Signature:", signature);
            } 
            catch (e) {
                console.warn("Failed", e);
                $('#status_text').text("Giao dịch thất bại!");
                $('#loginToast').toast('show');
                setTimeout(function() {
                    $('#loginToast').toast('hide');
                }, 5000);
            }
        })();
        
        async function setWalletTransaction(instruction, connection) {
            const transaction = new solanaWeb3.Transaction();
            transaction.add(instruction);
            transaction.feePayer = wallet.publicKey;
            let { blockhash } = await connection.getLatestBlockhash();
            console.log("blockhash", blockhash);
            transaction.recentBlockhash = blockhash;
            return transaction;
        }
      
        async function signAndSendTransaction(wallet, transaction, connection) {
            // Sign transaction, broadcast, and confirm
            const { signature } = await window.solana.signAndSendTransaction(
                transaction
            );
            await connection.confirmTransaction(signature);
            return signature;
        }
    }
	</script>
	
</body>
</html>
