﻿CREATE DATABASE APP_MOVIE_TICKET2;
go

USE APP_MOVIE_TICKET2;
GO

/*
select * from Users
select * from Cinemas
select * from CinemaRoom
select * from Genre
select * from Movies
select * from MovieGenre
select * from Rate
select * from Favourite
select * from BuyTicket 
select * from ComBo
select * from Seats
*/


CREATE TABLE Users (
    UserId INT PRIMARY KEY IDENTITY(1,1), -- Thiết lập UserId tự động tăng
    UserName VARCHAR(50) NOT NULL,
    Password VARCHAR(55) NOT NULL,
    Email VARCHAR(155) NOT NULL,
    FullName NVARCHAR(155) NOT NULL,
    PhoneNumber VARCHAR(20) NOT NULL,
    Photo VARCHAR(50),
    Role INT NOT NULL, -- Sử dụng TINYINT để lưu trữ nhiều giá trị, 0: khach hàng, 1: nhân viên, 2 quản lý, 3: admin
        CreateDate Datetime not null,
        Status NVARCHAR(20) not null, 
        IsDelete BIT not null, -- 0: false, 1: true;
);
go
CREATE TABLE Shifts (
    ShiftId INT PRIMARY KEY IDENTITY(1,1), -- ID tự động tăng cho mỗi ca làm
    ShiftName NVARCHAR(100) NOT NULL, -- Tên ca làm việc (ví dụ: Ca sáng, Ca tối)
    StartTime TIME NOT NULL, -- Giờ bắt đầu ca (chỉ lưu thời gian)
    EndTime TIME NOT NULL, -- Giờ kết thúc ca (chỉ lưu thời gian)
    IsCrossDay BIT NOT NULL, -- 0: Cùng ngày, 1: Qua ngày khác
    CreateDate DATETIME DEFAULT GETDATE(), -- Ngày tạo ca làm
    Status NVARCHAR(20) NOT NULL -- Trạng thái của ca (e.g., "Active", "Inactive")
);
GO

CREATE TABLE Locations (
    LocationId INT PRIMARY KEY IDENTITY(1,1), -- ID tự động tăng cho mỗi vị trí
    LocationName NVARCHAR(255) NOT NULL, -- Tên hoặc mô tả vị trí (ví dụ: Văn phòng Hà Nội)
    Latitude VARCHAR(50) NOT NULL, -- Tọa độ vĩ độ
    Longitude VARCHAR(50) NOT NULL, -- Tọa độ kinh độ
    Radius FLOAT NOT NULL, -- Bán kính chấm công tính bằng mét
    ShiftId INT NOT NULL, -- Liên kết đến bảng Shifts để xác định vị trí cho ca làm việc
    CONSTRAINT FK_Locations_Shifts FOREIGN KEY (ShiftId) REFERENCES Shifts(ShiftId) -- Khóa ngoại liên kết với bảng Shifts
);
GO


CREATE TABLE Attendance (
    AttendanceId INT PRIMARY KEY IDENTITY(1,1), -- ID tự động tăng
    UserId INT NOT NULL, -- Khóa ngoại liên kết đến bảng Users
    ShiftId INT NOT NULL, -- Thông tin ca làm việc
    CheckInTime DATETIME NOT NULL, -- Thời gian bắt đầu chấm công
    CheckOutTime DATETIME, -- Thời gian kết thúc chấm công, có thể null khi chưa checkout
    Latitude VARCHAR(50) NOT NULL, -- Tọa độ vĩ độ
    Longitude VARCHAR(50) NOT NULL, -- Tọa độ kinh độ
    Location NVARCHAR(255), -- Tên hoặc mô tả vị trí chấm công
    IsLate BIT NOT NULL, -- 0: Đúng giờ, 1: Đi trễ
    IsEarlyLeave BIT NOT NULL, -- 0: Không về sớm, 1: Về sớm
    CreateDate DATETIME DEFAULT GETDATE(), -- Ngày tạo bản ghi chấm công
    Status NVARCHAR(20) NOT NULL, -- Trạng thái chấm công (e.g., "Completed", "Pending")
    CONSTRAINT FK_Attendance_User FOREIGN KEY (UserId) REFERENCES Users(UserId), -- Khóa ngoại đến bảng Users
    CONSTRAINT FK_Attendance_Shift FOREIGN KEY (ShiftId) REFERENCES Shifts(ShiftId) -- Khóa ngoại đến bảng Shifts
);
GO



-- BẢNG Users CHUẨN

-- + 1 bảng lịch sử hoạt động của users
-- + 1 bảng lịch sử hoạt động của admin

CREATE TABLE Cinemas(
    CinemaID INT PRIMARY KEY IDENTITY(1,1),  -- Mã rạp, tự động tăng
    CinemaName NVARCHAR(100) NOT NULL,       -- Tên rạp
    Address NVARCHAR(255) NOT NULL           -- Địa chỉ của rạp
);
go

CREATE TABLE CinemaRoom (
        CinemaRoomID INT PRIMARY KEY,
    CinemaID INT,  -- Mã rạp
        FOREIGN KEY (CinemaID) REFERENCES Cinemas(CinemaID)

);
go


-- BẢNG Cinemas CHUẨN

CREATE TABLE Genre (
    IdGenre INT PRIMARY KEY IDENTITY(1,1),
    GenreName NVARCHAR(100) NOT NULL
);
go


CREATE TABLE Movies (
    MovieID INT PRIMARY KEY IDENTITY(1,1),
        CinemaID INT,
    Title NVARCHAR(255) NOT NULL,
    Description NVARCHAR(MAX) NOT NULL,
    Duration INT NOT NULL,
    ReleaseDate DATE NOT NULL,
    PosterUrl VARCHAR(255),
    TrailerUrl VARCHAR(255),
    Age NVARCHAR(20)  NOT NULL,
        SubTitle BIT,
        Voiceover BIT,
        StatusMovie NVARCHAR (20) NOT NULL,
        Price float NOT NULL,
        IsDelete BIT NOT NULL
        CONSTRAINT FK_CinemaID FOREIGN KEY (CinemaID) REFERENCES Cinemas (CinemaID),
);
go


CREATE TABLE Actors (
    ActorID INT PRIMARY KEY IDENTITY(1,1),
    Name NVARCHAR(255) NOT NULL,
);
GO
CREATE TABLE MovieActors (
    MovieID INT,
    ActorID INT,
    PRIMARY KEY (MovieID, ActorID),
    CONSTRAINT FK_MovieActors_MovieID FOREIGN KEY (MovieID) REFERENCES Movies (MovieID),
    CONSTRAINT FK_MovieActors_ActorID FOREIGN KEY (ActorID) REFERENCES Actors (ActorID)
);
GO


CREATE TABLE MovieGenre (
        IdmovieGenre INT PRIMARY KEY IDENTITY(1,1),
    MovieID INT NOT NULL,
    IdGenre INT NOT NULL,
    FOREIGN KEY (MovieID) REFERENCES Movies(MovieID),
    FOREIGN KEY (IdGenre) REFERENCES Genre(IdGenre)
);
go


CREATE TABLE Rate (
    IdRate INT PRIMARY KEY IDENTITY(1,1),
    MovieID INT NOT NULL,
    UserId INT NOT NULL,
    Content NVARCHAR(MAX) NOT NULL,
    Rating FLOAT NOT NULL,
    FOREIGN KEY (MovieID) REFERENCES Movies(MovieID),
    FOREIGN KEY (UserId) REFERENCES Users(UserId)
);
go
CREATE TABLE Favourite (
    IdFavourite INT PRIMARY KEY IDENTITY(1,1),
    MovieID INT NOT NULL,
    UserId INT NOT NULL,
    FOREIGN KEY (MovieID) REFERENCES Movies(MovieID),
    FOREIGN KEY (UserId) REFERENCES Users(UserId)
);
go
CREATE TABLE BuyTicket (
    BuyTicketId INT PRIMARY KEY,
    UserId INT NOT NULL,
    MovieID INT NOT NULL,
    FOREIGN KEY (MovieID) REFERENCES Movies(MovieID),
    FOREIGN KEY (UserId) REFERENCES Users(UserId)
);
go

CREATE TABLE Showtime (
    ShowtimeID INT PRIMARY KEY IDENTITY(1,1),    -- Mã lịch chiếu, tự động tăng
    MovieID INT NOT NULL,                        -- Mã phim
    CinemaRoomID INT NOT NULL,                   -- Mã phòng chiếu
    ShowtimeDate DATE NOT NULL,                  -- Ngày chiếu
    StartTime TIME NOT NULL,                     -- Giờ bắt đầu
    CONSTRAINT FK_MovieID FOREIGN KEY (MovieID) REFERENCES Movies(MovieID),
    CONSTRAINT FK_CinemaRoomID FOREIGN KEY (CinemaRoomID) REFERENCES CinemaRoom(CinemaRoomID)
);
GO


CREATE TABLE ComBo (
    ComboID INT PRIMARY KEY IDENTITY(1,1),
    Quantity int NOT NULL,
    Price float NOT NULL
);

go

CREATE TABLE BuyTicketInfo (
    BuyTicketInfoId INT PRIMARY KEY IDENTITY(1,1),
    BuyTicketId INT NOT NULL,
    Quantity int NOT NULL,
    CreateDate Datetime NOT NULL,
    TotalPrice float NOT NULL,
    ComboID int,
	ShowtimeID int not null,
    FOREIGN KEY (BuyTicketId) REFERENCES BuyTicket(BuyTicketId),
    FOREIGN KEY (ShowtimeID) REFERENCES Showtime(ShowtimeID),
	FOREIGN KEY (ComboID) REFERENCES ComBo(ComboID)
);

go



-- tạo bảng chứa ghế
CREATE TABLE Seats (
    SeatID INT PRIMARY KEY IDENTITY(1,1),      -- Mã ghế tự động tăng
    CinemaRoomID INT NOT NULL,                 -- Mã phòng chiếu
    ChairCode NVARCHAR(10) NOT NULL,           -- Mã ghế (Ví dụ: A1, A2, ...)
    DefectiveChair BIT NOT NULL DEFAULT 0,             -- Trạng thái ghế (0: bình thường, 1: Đang sửa)
    FOREIGN KEY (CinemaRoomID) REFERENCES CinemaRoom(CinemaRoomID)
);
GO


-- tạo bảng trung gian chứa ghế

CREATE TABLE SeatReservation (
    ReservationID INT PRIMARY KEY IDENTITY(1,1), -- Mã đặt ghế, tự động tăng
    ShowtimeID INT NOT NULL,                    -- Mã lịch chiếu (tham chiếu từ bảng Showtime)
    SeatID INT NOT NULL,                        -- Mã ghế (tham chiếu từ bảng Seats)
    Status BIT NOT NULL DEFAULT 0,              -- Trạng thái ghế (0: chưa đặt, 1: đã đặt)
    CONSTRAINT FK_ShowtimeID FOREIGN KEY (ShowtimeID) REFERENCES Showtime(ShowtimeID),
    CONSTRAINT FK_SeatID FOREIGN KEY (SeatID) REFERENCES Seats(SeatID)
);
GO
-- tạo bảng trung gian chứa ghế và vé

CREATE TABLE TicketSeat (
    TicketSeatID INT PRIMARY KEY IDENTITY(1,1), -- Khóa chính tự động tăng
    BuyTicketId INT NOT NULL,                   -- Mã vé đã mua
    SeatID INT NOT NULL,                        -- Mã ghế đã đặt
    FOREIGN KEY (BuyTicketId) REFERENCES BuyTicket(BuyTicketId),  -- Ràng buộc khóa ngoại với BuyTicket
    FOREIGN KEY (SeatID) REFERENCES Seats(SeatID)                 -- Ràng buộc khóa ngoại với Seats
);
go
/*
SELECT 
    BT.BuyTicketId,
    BTI.CreateDate,
    BTI.Quantity,
    BTI.TotalPrice,
    M.Title AS MovieTitle,
    M.PosterUrl,
    ST.ShowtimeDate,
    ST.StartTime,
    C.CinemaName,
    CR.CinemaRoomID,
    STRING_AGG(S.ChairCode, ', ') AS SeatNumbers
FROM 
    BuyTicket BT
INNER JOIN 
    BuyTicketInfo BTI ON BT.BuyTicketId = BTI.BuyTicketId
INNER JOIN 
    Movies M ON BT.MovieID = M.MovieID
INNER JOIN 
    Showtime ST ON BTI.ShowtimeID = ST.ShowtimeID
INNER JOIN 
    CinemaRoom CR ON ST.CinemaRoomID = CR.CinemaRoomID
INNER JOIN 
    Cinemas C ON CR.CinemaID = C.CinemaID
INNER JOIN 
    TicketSeat TS ON BT.BuyTicketId = TS.BuyTicketId
INNER JOIN 
    Seats S ON TS.SeatID = S.SeatID
WHERE 
    BT.UserId = 1
GROUP BY 
    BT.BuyTicketId, BTI.CreateDate, BTI.Quantity, BTI.TotalPrice, 
    M.Title, M.PosterUrl, ST.ShowtimeDate, ST.StartTime, 
    C.CinemaName, CR.CinemaRoomID
ORDER BY 
    BTI.CreateDate DESC
	*/
-- insert:
-- BẢNG Users 
INSERT INTO Users (UserName, Password, Email, FullName, PhoneNumber, Photo, Role, CreateDate, Status,IsDelete)
VALUES 
('minhduc1122003', '123123', 'user1@example.com', N'Lê Minh Đức KH', '123456789', null, 0, GETDATE(), N'Đang hoạt động',0),
('minhduc11220031', '123123', 'user1@example.com', N'Lê Minh Đức NV', '123456789', null, 1, GETDATE(), N'Đang hoạt động',0),
('minhduc11220032', '123123', 'user1@example.com', N'Lê Minh Đức AD', '123456789', null, 2, GETDATE(), N'Đang hoạt động',0),
('user1', 'password1', 'user1@example.com', N'User 1', '1234567890', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user2', 'password2', 'user2@example.com', N'User 2', '1234567891', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user3', 'password3', 'user3@example.com', N'User 3', '1234567892', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user4', 'password4', 'user4@example.com', N'User 4', '1234567893', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user5', 'password5', 'user5@example.com', N'User 5', '1234567894', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user6', 'password6', 'user6@example.com', N'User 6', '1234567895', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user7', 'password7', 'user7@example.com', N'User 7', '1234567896', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user8', 'password8', 'user8@example.com', N'User 8', '1234567897', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user9', 'password9', 'user9@example.com', N'User 9', '1234567898', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user10', 'password10', 'user10@example.com', N'User 10', '1234567899', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user11', 'password11', 'user11@example.com', N'User 11', '1234567800', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user12', 'password12', 'user12@example.com', N'User 12', '1234567801', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user13', 'password13', 'user13@example.com', N'User 13', '1234567802', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user14', 'password14', 'user14@example.com', N'User 14', '1234567803', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user15', 'password15', 'user15@example.com', N'User 15', '1234567804', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user16', 'password16', 'user16@example.com', N'User 16', '1234567805', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user17', 'password17', 'user17@example.com', N'User 17', '1234567806', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user18', 'password18', 'user18@example.com', N'User 18', '1234567807', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user19', 'password19', 'user19@example.com', N'User 19', '1234567808', null, 1, GETDATE(), N'Đang hoạt động', 0),
('user20', 'password20', 'user20@example.com', N'User 20', '1234567809', null, 1, GETDATE(), N'Đang hoạt động', 0);
go


-- BẢNG rạp phim
INSERT INTO Cinemas (CinemaName, Address)
VALUES 
(N'Panthers Cinemar', N'Nguyễn Văn Quá, Quận 12, TP.HCM')
go

-- Insert dữ liệu cho bảng CinemaRoom ( phòng chiếu )
INSERT INTO CinemaRoom (CinemaID,CinemaRoomID)
VALUES 
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(1,6);
go
-- Insert dữ liệu cho bảng Thể loại (Genre)
INSERT INTO Genre (GenreName)
VALUES 
(N'Hành động'),
(N'Phiêu lưu'),
(N'Hài'),
(N'Chính kịch'),
(N'Tâm lý'),
(N'Kinh dị'),
(N'Tội phạm'),
(N'Tình cảm'),
(N'Khoa học viễn tưởng'),
(N'Giả tưởng'),
(N'Hoạt hình'),
(N'Chiến tranh'),
(N'Âm nhạc'),
(N'Tài liệu'),
(N'Gia đình'),
(N'Thần thoại'),
(N'Lịch sử'),
(N'Hình sự'),
(N'Bí ẩn'),
(N'Võ thuật'),
(N'Siêu anh hùng'),
(N'Viễn Tây');
go


DECLARE @RoomID INT = 1;  -- RoomID của phòng chiếu bắt đầu
DECLARE @Row CHAR(1);  -- Hàng ghế (A, B, C,...)
DECLARE @SeatNumber INT;  -- Số ghế (1, 2, 3,...)

WHILE @RoomID <= 6  -- Giả sử có 6 phòng chiếu
BEGIN
    SET @Row = 'A';  -- Bắt đầu từ hàng 'A'
    
    -- Duyệt qua từng hàng từ 'A' đến 'M'
    WHILE ASCII(@Row) <= ASCII('J')  
    BEGIN
        SET @SeatNumber = 1;  -- Đặt lại số ghế bắt đầu từ 1
        
        -- Duyệt qua từng ghế từ 1 đến 16 trong mỗi hàng
        WHILE @SeatNumber <= 14
        BEGIN
            -- Xác định mã ghế theo hàng và số ghế (ví dụ A1, A2,... M16,...)
            INSERT INTO Seats (CinemaRoomID, ChairCode, DefectiveChair)
            VALUES (@RoomID, @Row + CAST(@SeatNumber AS NVARCHAR(10)), 0);
            
            -- Tăng số ghế lên
            SET @SeatNumber = @SeatNumber + 1;
        END
        
        -- Chuyển sang hàng tiếp theo (A -> B -> C, v.v.)
        SET @Row = CHAR(ASCII(@Row) + 1);  
    END
    
    -- Chuyển sang phòng chiếu tiếp theo
    SET @RoomID = @RoomID + 1;
END;
GO


-- Insert dữ liệu cho bảng Movies(danh sách phim)

INSERT INTO Movies (CinemaID, Title, Description, Duration, ReleaseDate, PosterUrl, TrailerUrl, Age, SubTitle, Voiceover,StatusMovie,Price,IsDelete)
VALUES 
(1, N'Làm Giàu Với Ma', N'Kể về Lanh (Tuấn Trần) - con trai của ông Đạo làm nghề mai táng (Hoài Linh), lâm vào đường cùng vì cờ bạc. Trong cơn túng quẫn, “duyên tình” đẩy đưa anh gặp một ma nữ (Diệp Bảo Ngọc) và cùng nhau thực hiện những “kèo thơm" để phục vụ mục đích của cả hai.', 120, '2024-08-30', 'lamgiauvoima.jpg', 'https://youtu.be/2DmOv-pM1bM', 'K', 1, 0,N'Đang chiếu',50000,0),
(1, N'Tìm Kiếm Tài Năng Âm Phủ', N'Newbie - một hồn ma mới, kinh hoàng nhận ra rằng cô chỉ còn 28 ngày nữa cho đến khi linh hồn của cô biến mất khỏi thế giới. Makoto, một đặc vụ quỷ tiếp cận Newbie với lời đề nghị cô kết hợp cùng ngôi sao quỷ Catherine để dựng lại câu chuyện kinh dị huyền thoại về khách sạn Wang Lai. Nếu câu chuyện đủ sức hù dọa người sống thì cái tên của cô sẽ trở thành huyền thoại và linh hồn của Newbie sẽ tiếp tục được sống dưới địa ngục.', 112, '2024-09-13', 'timkiemtainangamphu.jpg', 'https://youtu.be/KsnXHxMkf70', 'P', 1, 0,N'Đang chiếu',50000,0),
(1, N'Không Nói Điều Dữ', N'Chuyển thể từ tựa phim kinh dị nổi tiếng của Đan Mạch Gæsterne (Speak No Evil), được phát hành vào năm 2022 và đã nhận được 11 đề cử Giải thưởng Điện ảnh Đan Mạch, tương đương với giải Oscar của Đan Mạch. Bộ phim đánh dấu sự quay lại của hai tên tuổi trong làng kinh dị là Blumhouse cùng phù thủy của những nỗi sợ - James Wan. Kì nghỉ tuyệt vời của gia đình Ben bỗng trở thành cơn ác mộng khi họ làm quen với ông bà Paddy. Nhận lời mời đến nhà, Ben và Louise dần phát hiện ra những bí mật đen tối. Gã bạn mới hiện nguyên hình là một tên khát máu, hắn bắt người ăn chay trường như Louise phải ăn thịt, đánh đập đứa con và làm ra những hành động điên rồ. Ant - cậu con trai không nói được của Paddy và Ciara, liên tục thể hiện hành động kỳ lạ, cố gắng giao tiếp bằng ngôn ngữ hình thể với Agnes, con gái Ben và Louise. Cậu bé đưa Agnes đến căn hầm bí mật, nơi ẩn chứa bí mật đáng sợ trong chính ngôi nhà, một phần bóng tối dần được phơi bày. Gia đình Ben phải lao vào cuộc chiến sinh tử để thoát khỏi gã Paddy điên loạn, nhưng họ có thực sự thoát được gia đình đáng sợ kia?', 110, '2024-09-13', 'khongnoidieudu.jpg', 'https://youtu.be/T-TQAfES10g', '13', 1, 0,N'Đang chiếu',50000,0),
(1, N'Joker', N'Lấy bối cảnh thành phố Gotham những năm 80, Arthur Fleck lớn lên trong sự cô đơn, luôn phải cười vì lời dạy thuở nhỏ của mẹ. Nghèo khó cơ cực nên anh ta phải làm chú hề mua vui trên phố. Thế nhưng, dù khuôn mặt chú hề luôn cười nhưng nội tâm Arthur lại có vô vàn nỗi đau khi thương xuyên bị chà đạp, khinh khi. Cuối cùng, hắn trở nên điên loạn và trở thành "Hoàng tử tội phạm" Joker. Dù không liên quan đến vũ trụ điện ảnh DC mở rộng, Joker vẫn được các fan hâm mộ hết sức quan tâm.', 110, '2024-10-04', 'joker.jpg', 'https://youtu.be/Wh28HYiM80Y', 'K', 1, 0,N'Đang chiếu',50000,0),

(1, N'Quỷ Án', N'Kể về vụ án người phụ nữ Dani bị sát hại dã man tại ngôi nhà mà vợ chồng cô đang sửa sang ở vùng nông thôn hẻo lánh. Chồng cô - Ted đang làm bác sĩ tại bệnh viện tâm thần. Mọi nghi ngờ đổ dồn vào một bệnh nhân tại đây. Không may, nghi phạm đã chết. Một năm sau, em gái mù của Dani ghé tới. Darcy là nhà ngoại cảm tự xưng, mang theo nhiều món đồ kì quái. Cô đến nhà Ted để tìm chân tướng về cái chết của chị gái.', 110, '2024-09-13', 'quyan.jpg', 'https://youtu.be/RA5qp5btmT8', '16', 1, 0,N'Đang chiếu',50000,0),

(1, N'The Crow: Báo Thù', N'Câu chuyện phim là dị bản kinh dị đẫm máu lấy cảm hứng từ truyện cổ tích nổi tiếng Tấm Cám, nội dung chính của phim xoay quanh Cám - em gái cùng cha khác mẹ của Tấm đồng thời sẽ có nhiều nhân vật và chi tiết sáng tạo, gợi cảm giác vừa lạ vừa quen cho khán giả. Sau loạt tác phẩm kinh dị ăn khách như Tết Ở Làng Địa Ngục, Kẻ Ăn Hồn... bộ đôi nhà sản xuất Hoàng Quân - đạo diễn Trần Hữu Tấn đã tiếp tục với một dị bản của cổ tích Việt Nam mang tên Cám. Cùng dàn diễn viên tiềm năng, vai Tấm do diễn viên Rima Thanh Vy thủ vai, trong khi vai Cám được trao cho gương mặt rất quen thuộc - Lâm Thanh Mỹ. Ngoài ra vai mẹ kế của diễn viên Thúy Diễm và vai Hoàng tử do Hải Nam đảm nhận. Dị bản sẽ cho một góc nhìn hoàn toàn khác về Tấm Cám khi sự thay đổi đến từ người nuôi cá bống lại là Cám. Cô bé có ngoại hình dị dạng, khiến cả gia đình bị dân làng cho là phù thủy. Cũng vì thế mà Cám mới là đứa con bị đối xử tệ bạc, bắt phải lựa gạo chứ không phải Tấm. Cùng với bài đồng dao về cá bống, giọng nói của Bụt trong phim mới cũng vang lên khi hỏi: “Vì sao con khóc?”. Thế nhưng, nó không mang màu sắc dịu hiền, thân thương của một vì thần tiên trong văn hóa Việt Nam mà đậm chất ma mị, kinh dị. Liệu đây có đúng là Bụt hay chính là ác quỷ đội lốt đã lừa dối Tấm và Cám từ lâu để đưa họ vào cái bẫy chết chóc?', 122, '2024-09-20', 'thecrowbaothu.jpg', 'https://youtu.be/RA5qp5btmT8', '18', 1, 1,N'Đang Chiếu',50000,0),
(1, N'Anh Trai Vượt Mọi Tam Tai', N'Kể về Cho Su-gwang là một thanh tra cực kỳ nóng tính, dù có tỷ lệ bắt giữ tội phạm ấn tượng nhưng anh luôn gặp khó khăn trong việc kiểm soát cơn giận của mình. Vì liên tục tấn công các nghi phạm, Cho Su-gwang bị chuyển đến đảo Jeju. Tại đây, vị thanh tra nhận nhiệm vụ truy bắt kẻ lừa đảo giỏi nhất Hàn Quốc - Kim In-hae với 7 tiền án, nổi tiếng thông minh và có khả năng “thiên biến vạn hoá” để ngụy trang hoàn hảo mọi nhân dạng. Cùng lúc đó, Kim In-hae bất ngờ dính vào vụ án mạng nghiêm trọng có liên quan đến tên trùm xã hội đen đang nhăm nhe “thôn tính” đảo Jeju. Trước tình hình nguy cấp phải “giải cứu” hòn đảo Jeju và triệt phá đường dây nguy hiểm của tên trùm xã hội đen, thanh tra Cho Su-gwang bất đắc dĩ phải hợp tác cùng nghi phạm Kim In-hae, tận dụng triệt để các kỹ năng từ phá án đến lừa đảo trên hành trình rượt đuổi vừa gay cấn vừa hài hước để có thể hoàn thành nhiệm vụ cam go.', 96, '2024-09-13', 'anhtraivuotmoitamtai.jpg', 'https://youtu.be/OmmAlqNgkNI', '13', 1, 0,N'Đang chiếu',50000,0),
(1, N'Báo Thủ Đi Tìm Chủ', N'Kể về cún cưng Gracie và mèo Pedro tinh nghịch bị lạc khỏi chủ trong một lần chuyển nhà. Các “báo thủ” bắt đầu cuộc hành trình vượt ngàn chông gai, được cứu nguy bởi bài hát viral của chủ nhân, đối đầu với các nhân vật có tiếng trong giới mộ điệu cho đến khi đoàn tụ với Sophie và Gavin để tìm đường về nhà.', 96, '2024-09-13', 'baothuditimchu.jpg', 'https://youtu.be/OmmAlqNgkNI', 'K', 1, 1, N'Đang chiếu',56000,0),
(1, N'Longlegs: Thảm Kịch Dị Giáo', N'Theo chân một đặc vụ FBI do Maika Monroe thủ vai. Cô được giao điều tra một vụ án liên quan đến kẻ giết người hàng loạt, nổi tiếng với việc để lại các dòng chữ mã hóa ở hiện trường vụ án.', 101, '2024-09-06', 'thamkichdigiao.jpg', 'https://youtu.be/pvPRijZ8dWI','18', 1, 0,N'Đang chiếu',70000,0),


-- insert sắp chiếu

(1, N'Cám', N'Câu chuyện phim là dị bản kinh dị đẫm máu lấy cảm hứng từ truyện cổ tích nổi tiếng Tấm Cám, nội dung chính của phim xoay quanh Cám - em gái cùng cha khác mẹ của Tấm đồng thời sẽ có nhiều nhân vật và chi tiết sáng tạo, gợi cảm giác vừa lạ vừa quen cho khán giả. Sau loạt tác phẩm kinh dị ăn khách như Tết Ở Làng Địa Ngục, Kẻ Ăn Hồn... bộ đôi nhà sản xuất Hoàng Quân - đạo diễn Trần Hữu Tấn đã tiếp tục với một dị bản của cổ tích Việt Nam mang tên Cám. Cùng dàn diễn viên tiềm năng, vai Tấm do diễn viên Rima Thanh Vy thủ vai, trong khi vai Cám được trao cho gương mặt rất quen thuộc - Lâm Thanh Mỹ. Ngoài ra vai mẹ kế của diễn viên Thúy Diễm và vai Hoàng tử do Hải Nam đảm nhận. Dị bản sẽ cho một góc nhìn hoàn toàn khác về Tấm Cám khi sự thay đổi đến từ người nuôi cá bống lại là Cám. Cô bé có ngoại hình dị dạng, khiến cả gia đình bị dân làng cho là phù thủy. Cũng vì thế mà Cám mới là đứa con bị đối xử tệ bạc, bắt phải lựa gạo chứ không phải Tấm. Cùng với bài đồng dao về cá bống, giọng nói của Bụt trong phim mới cũng vang lên khi hỏi: “Vì sao con khóc?”. Thế nhưng, nó không mang màu sắc dịu hiền, thân thương của một vì thần tiên trong văn hóa Việt Nam mà đậm chất ma mị, kinh dị. Liệu đây có đúng là Bụt hay chính là ác quỷ đội lốt đã lừa dối Tấm và Cám từ lâu để đưa họ vào cái bẫy chết chóc?', 122, '2024-09-20', 'cam.jpg', 'https://youtu.be/RA5qp5btmT8','K', 1, 1,N'Sắp chiếu',59000,0),
(1, N'Cô Dâu Hào Môn', N'Uyển Ân chính thức lên xe hoa trong thế giới thượng lưu của đạo diễn Vũ Ngọc Đãng qua bộ phim Cô Dâu Hào Môn. Thừa thắng xông lên sau doanh thu trăm tỷ từ Chị Chị Em Em 2, nhà sản xuất Will Vũ và đạo diễn Vũ Ngọc Đãng bắt tay thực hiện dự án Cô Dâu Hào Môn. Bộ phim xoay quanh câu chuyện làm dâu nhà hào môn dưới góc nhìn hài hước và châm biếm, hé lộ những câu chuyện kén dâu chọn rể trong giới thượng lưu. Phối hợp cùng Uyển Ân ở các phân đoạn tình cảm trong bộ phim lần này là diễn viên Samuel An. Anh được đạo diễn Vũ Ngọc Đãng “đo ni đóng giày” cho vai cậu thiếu gia Bảo Hoàng với ngoại hình điển trai, phong cách lịch lãm và gia thế khủng. Cùng góp mặt với Uyển Ân trong bộ phim đình đám lần này là sự xuất hiện của những cái tên bảo chứng phòng vé như: Thu Trang, Kiều Minh Tuấn, Samuel An, Lê Giang, NSND Hồng Vân,...', 122, '2024-10-18', 'codauhaomon.jpg', 'https://youtu.be/e9nGWTxlLDo','13', 1, 1,N'Sắp chiếu',80000,0),
(1, N'Hắn', N'Lấy bối cảnh vùng nông thôn Nhật Bản, khi hàng loạt cái chết kì lạ xảy ra. Tất cả các nạn nhân đều có điểm chung: Trước khi chết họ gặp một người đàn ông lạ mặt trong mơ. Nữ chính Yasaka cũng không ngoại lệ. Trước khi bi kịch xảy ra, cô đang sống hạnh phúc bên chồng con. Khi ngày càng nhiều người thân qua đời, cô bắt đầu sợ ngủ. Cuối cùng, cô nhìn thấy “người đàn ông” trong giấc mơ của mình. Cái kết tồi tệ nhất sắp xảy ra…?', 122, '2024-09-20', 'han.jpg', 'https://youtu.be/dTp7pUlNUMg', 'K', 1, 1,N'Sắp chiếu',60000,0),
(1, N'Transformer Một', N'Là bộ phim hoạt hình Transformers đầu tiên sau 40 năm, và để kỷ niệm 40 năm thương hiệu Transformers, bộ phim là câu chuyện gốc về quá trình Optimus Prime và Megatron từ bạn thành thù. Lấy chủ đề câu chuyện phiêu lưu hài hước tràn ngập tình đồng đội cùng những pha hành động và biến hình cực đã mắt, Transformer One hé lộ câu chuyện gốc được chờ đợi bấy lâu về cách các nhân vật mang tính biểu tượng nhất trong vũ trụ Transformers - Orion Pax và D-16 từ anh em chiến đấu trở thành Optimus Prime và Megatron - kẻ thù không đội trời chung. Với sự tham gia lồng tiếng của Chris Hemsworth và Scarlett Johansson, Transformers One hứa hẹn đưa thương hiệu Transformers lên “tầm cao mới”. Orion Pax và D-16 từng là robot công nhân “quèn” tại Cybertron. Hai robot “trẻ trâu” thường xuyên dính vào rắc rối. Những người máy thường bị cấm và chỉ được hoạt động dưới lòng đất vì bề mặt của hành tinh quê nhà là nơi cực kì nguy hiểm. Tuy nhiên, càng cấm thì càng tò mò! Orion, D-16 và các Cybertronians khác như Elita-1 và B-127 / Bumblebee quyết định thực hiện một chuyến phiêu lưu. Tại đây, họ đã chạm trán với nhiều dạng động thực vật cơ giới hóa xưa nay chưa từng thấy. Cuộc chạm trán với Alpha Trion giúp cho robot công nhân cấp thấp trở thành người máy biến hình cấu hình “xịn đét”. Ngoài ra, dường như, cũng thay đổi cả cách họ nhìn nhận thế giới, trở thành tiền đề sự chia rẽ về sau... Thời kì đầu về Optimus Prime và Megatron đã được khám phá trong loạt phim hoạt hình và truyện tranh nhưng Transformers One là tác phẩm chiếu rạp đầu tiên lấy chủ đề này. Theo nhà sản xuất loạt phim Transformers lâu năm Lorenzo di Bonaventura, Transformers One hứa hẹn quy mô khủng cỡ Unicron. Transformers One có sự tham gia của dàn diễn viên lồng tiếng cực chất bao gồm Chris Hemsworth, Brian Tyree Henry, Scarlett Johansson, Keegan-Michael Key, Jon Hamm, Laurence Fishburne và Steve Buscemi. Josh Cooley, cựu họa sĩ và nhà biên kịch Pixar, đạo diễn Toy Story 4, sẽ chỉ đạo bộ phim. Phim có Andrew Barrer và Gabriel Ferrari (Ant-Man and the Wasp) chịu trách nhiệm kịch bản. Michael Bay - đạo diễn nhiều phần phim Transformers ăn khách trước đây - là nhà sản xuất bộ phim bên cạnh Lorenzo di Bonaventura, Tom DeSanto, Don Murphy, Mark Vahradian và Aaron Dem.', 122, '2024-09-27', 'transformermot.jpg', 'https://youtu.be/YTP6joQcCho', 'P', 1, 0,N'Sắp chiếu',50000,0),
(1, N'Đố Anh Còng Được Tôi', N'Các thanh tra kỳ cựu nổi tiếng đã hoạt động trở lại! Thám tử Seo Do-cheol (HWANG Jung-min) và đội điều tra tội phạm nguy hiểm của anh không ngừng truy lùng tội phạm cả ngày lẫn đêm, đặt cược cả cuộc sống cá nhân của họ. Nhận một vụ án sát hại một giáo sư, đội thanh tra nhận ra những mối liên hệ với các vụ án trong quá khứ và nảy sinh những nghi ngờ về một kẻ giết người hàng loạt. Điều này đã khiến cả nước rơi vào tình trạng hỗn loạn. Khi đội thanh tra đi sâu vào cuộc điều tra, kẻ sát nhân đã chế nhạo họ bằng cách công khai tung ra một đoạn giới thiệu trực tuyến, chỉ ra nạn nhân tiếp theo và làm gia tăng sự hỗn loạn. Để giải quyết mối đe dọa ngày càng leo thang, nhóm đã kết nạp một sĩ quan tân binh trẻ Park Sun-woo (JUNG Hae-in), dẫn đến những khúc mắc và đầy rẫy bất ngờ trong vụ án.', 122, '2024-09-27', 'doanhcongduoctoi.jpg', 'https://youtu.be/Mb3f6ZDSty0', '16', 1, 0,N'Sắp chiếu',60000,0),
(1, N'Minh Hôn', N'Diễn ra sau khi mất vợ và con gái, Won Go Myeong – một pháp sư đầy hận thù, đãphát hiện ra gã tài phiệt đứng sau cái chết gia đình ông. Với ma thuật đen, Go Myeong đã gọi hồn, triệu vong vạch trần sự thật và khiến gã tài phiệt đền mạng. Thế nhưng, mọi chuyện chỉ là khởi đầu….', 122, '2024-09-27', 'minhhon.jpg', 'https://youtu.be/x7hgcR3u5xM', 'P', 1, 0,N'Sắp chiếu',70000,0),
(1, N'Hẹn Hò Với Sát Nhân', N'Cheryl Bradshaw (Anna Kendrick thủ vai) tham gia chương trình truyền hình về hẹn hò - The Dating Game với khát khao được nổi tiếng. Tại đây, cô nàng đã gặp gỡ Rodney Alcala - tên sát nhân đội lốt một nhiếp ảnh gia lãng tử và đối đáp cực kỳ hài hước, thông minh trong chương trình hẹn hò. Quyết định kết đôi cùng Rodney Alcala, trong quá trình hẹn hò, Cheryl Bradshaw dần khám phá ra hàng loạt bí mật gây sốc được che giấu khéo léo bởi cái lốt người đàn ông hoàn hảo: đội lốt một gã sát nhân, kẻ biến thái đã chủ mưu rất nhiều vụ hiếp dâm và giết người man rợ.', 122, '2024-09-27', 'henhovoisatnhan.jpg', 'https://youtu.be/64ePhFIKUA4', '18', 1, 0,N'Sắp chiếu',59000,0),
(1, N'Joker: Folie À Deux Điên Có Đôi', N'Không ngoa khi nói rằng, Joker là nhân vật phản diện nổi tiếng hàng đầu thế giới. Kẻ thù của Batman là cái tên mang tính biểu tượng từ truyện tranh đến màn ảnh rộng. Năm 2019, Todd Phillips và Joaquin Phoenix mang đến cho khán giả một Joker cực kì khác biệt, chưa từng có trong lịch sử. Phim thành công nhận 11 đề cử Oscar và thắng 2 giải, trong đó có Nam chính xuất sắc nhất cho Joaquin Phoenix. Lần này, Joker 2 trở lại, mang đến cho khán giả bộ đôi diễn viên trong mơ – Joaquin Phoenix tiếp tục trở thành Arthur Fleck còn vai diễn Harley Quinn thuộc về Lady Gaga. Chưa tham gia nhiều phim, nữ ca sĩ huyền thoại vẫn nhận được sự tin tưởng từ công chúng bởi diễn xuất chất lượng trong A Star Is Born (2018), House Of Gucci (2021). Folie À Deux là căn bệnh rối loạn tâm thần chia sẻ. Chứng bệnh khiến cả hai người cùng tiếp xúc với nguồn năng lực tiêu cực trong tâm trí. Dường như, ở Joker 2, gã hề đã “lây lan” căn bệnh đến Harley Quinn, khiến cả hai người họ “điên có đôi”. Tên phim đã khắc họa được một phần nội dung, xoáy sâu vào mối quan hệ độc hại giữa Joker và Harley Quinn. Ít nhất 15 bài hát nổi tiếng sẽ tái hiện lại trong Joker: Folie À Deux. Joker và Harley Quinn luôn đi kèm âm thanh từ bản nhạc bất hủ Close To You, What The World Needs Now,… Có lẽ, chỉ có âm nhạc mới thể hiện nổi sự điên loạn và chứng rối loạn ảo tưởng. Ngoài ra, âm nhạc còn giúp Joker: Folie À Deux khác biệt với tác phẩm thuộc DC Comic từ trước tới nay cũng như phát huy sở trường của Lady Gaga. Tất nhiên, dù hát ca nhiều bao nhiêu, phim vẫn dán nhãn R, tràn ngập bạo lực.', 122, '2024-10-04', 'joker_folieadeuxdiencodoi.jpg', 'https://youtu.be/n2k54qx9YkE', '13', 1, 0,N'Sắp chiếu',55000,0),
(1, N'Joker: Folie À Deux', N'Không ngoa khi nói rằng, Joker là nhâ đến mànmang đến cho khán giả một Joker cực kì khác biệt, chưa từng có trong lịch sử. Phim thành công nhận 11 đề cử Oscar và thắng 2 giải, trong đó có Nam chính xuất sắc nhất cho Joaquin Phoenix. Lần này, Joker 2 trở lại, mang đến cho khán giả bộ đôi diễn viên trong mơ – Joaquin Phoenix tiếp tục trở thành Arthur Fleck còn vai diễn Harley Quinn thuộc về Lady Gaga. Chưa tham gia nhiều phim, nữ ca sĩ huyền thoại vẫn nhận được sự tin tưởng từ công chúng bởi diễn xuất chất lượng trong A Star Is Born (2018), House Of Gucci (2021). Folie À Deux là căn bệnh rối loạn tâm thần chia sẻ. Chứng bệnh khiến cả hai người cùng tiếp xúc với nguồn năng lực tiêu cực trong tâm trí. Dường như, ở Joker 2, gã hề đã “lây lan” căn bệnh đến Harley Quinn, khiến cả hai người họ “điên có đôi”. Tên phim đã khắc họa được một phần nội dung, xoáy sâu vào mối quan hệ độc hại giữa Joker và Harley Quinn. Ít nhất 15 bài hát nổi tiếng sẽ tái hiện lại trong Joker: Folie À Deux. Joker và Harley Quinn luôn đi kèm âm thanh từ bản nhạc bất hủ Close To You, What The World Needs Now,… Có lẽ, chỉ có âm nhạc mới thể hiện nổi sự điên loạn và chứng rối loạn ảo tưởng. Ngoài ra, âm nhạc còn giúp Joker: Folie À Deux khác biệt với tác phẩm thuộc DC Comic từ trước tới nay cũng như phát huy sở trường của Lady Gaga. Tất nhiên, dù hát ca nhiều bao nhiêu, phim vẫn dán nhãn R, tràn ngập bạo lực.', 122, '2024-12-04', 'joker_folieadeuxdiencodoi.jpg', 'https://www.youtube.com/watch?v=9j5RuZi1FzA', '18', 1, 0,N'Sắp chiếu',556000,0);
go

-- insert bảng phim - thể loại
INSERT INTO MovieGenre(MovieID, IdGenre)
VALUES 
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 4),
(3, 2),
(3, 1),
(3, 6),
(4, 2),
(4, 8),
(5, 1),
(5, 2),
(6, 2),
(6, 8),
(6, 1),
(6, 5),
(7, 2),
(7, 1),
(7, 6),
(8, 2),
(8, 8),
(9, 1),
(9, 2),
(10, 2),
(10, 1),
(10, 6),
(11, 2),
(11, 8),
(12, 1),
(12, 2),
(13, 2),
(13, 8),
(13, 1),
(13, 5),
(14, 2),
(14, 1),
(15, 2),
(15, 3),
(16, 1),
(16, 4),
(17, 2),
(17, 1),
(18, 1),
(17, 6);
go




-- Lịch chiếu từ Rạp 1

INSERT INTO Showtime (MovieID, CinemaRoomID, ShowtimeDate, StartTime) 
VALUES 
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-30', '09:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-30', '11:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-30', '13:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-30', '15:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-30', '18:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-30', '20:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-30', '22:30');
go
INSERT INTO Showtime (MovieID, CinemaRoomID, ShowtimeDate, StartTime) 
VALUES 
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-30', '09:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-30', '11:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-30', '14:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-30', '16:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-30', '18:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-30', '20:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-30', '23:00');
go

INSERT INTO Showtime (MovieID, CinemaRoomID, ShowtimeDate, StartTime) 
VALUES 
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-30', '10:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-30', '12:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-30', '14:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-30', '16:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-30', '19:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-30', '21:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-30', '23:30');
go
INSERT INTO Showtime (MovieID, CinemaRoomID, ShowtimeDate, StartTime) 
VALUES 
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 4, '2024-09-30', '10:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 4, '2024-09-30', '12:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 4, '2024-09-30', '15:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 4, '2024-09-30', '17:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 4, '2024-09-30', '19:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 4, '2024-09-30', '22:00');
go
INSERT INTO Showtime (MovieID, CinemaRoomID, ShowtimeDate, StartTime) 
VALUES 
((SELECT MovieID FROM Movies WHERE Title = N'Tìm Kiếm Tài Năng Âm Phủ'), 5, '2024-09-30', '09:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Joker'), 5, '2024-09-30', '11:15'),
((SELECT MovieID FROM Movies WHERE Title = N'The Crow: Báo Thù'), 5, '2024-09-30', '13:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Báo Thủ Đi Tìm Chủ'), 5, '2024-09-30', '15:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Tìm Kiếm Tài Năng Âm Phủ'), 5, '2024-09-30', '18:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Joker'), 5, '2024-09-30', '20:15'),
((SELECT MovieID FROM Movies WHERE Title = N'The Crow: Báo Thù'), 5, '2024-09-30', '22:30');
go

INSERT INTO Showtime (MovieID, CinemaRoomID, ShowtimeDate, StartTime) 
VALUES 
((SELECT MovieID FROM Movies WHERE Title = N'Không Nói Điều Dữ'), 6, '2024-09-30', '09:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Quỷ Án'), 6, '2024-09-30', '11:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Anh Trai Vượt Mọi Tam Tai'), 6, '2024-09-30', '14:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Longlegs: Thảm Kịch Dị Giáo'), 6, '2024-09-30', '16:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Không Nói Điều Dữ'), 6, '2024-09-30', '18:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Quỷ Án'), 6, '2024-09-30', '20:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Anh Trai Vượt Mọi Tam Tai'), 6, '2024-09-30', '23:00');
go
----------------------------- ngày 29/09/2024-------------------------------------------


INSERT INTO Showtime (MovieID, CinemaRoomID, ShowtimeDate, StartTime) 
VALUES 
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-29', '09:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-29', '11:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-29', '13:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-29', '15:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-29', '18:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-29', '20:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 1, '2024-09-29', '22:30');
go
-- Rạp 2
INSERT INTO Showtime (MovieID, CinemaRoomID, ShowtimeDate, StartTime) 
VALUES 
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-29', '09:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-29', '11:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-29', '14:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-29', '16:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-29', '18:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-29', '20:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 2, '2024-09-29', '23:00');
go
-- Rạp 3
INSERT INTO Showtime (MovieID, CinemaRoomID, ShowtimeDate, StartTime) 
VALUES 
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-29', '10:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-29', '12:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-29', '14:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-29', '16:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-29', '19:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-29', '21:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 3, '2024-09-29', '23:30');
go
-- Rạp 4
INSERT INTO Showtime (MovieID, CinemaRoomID, ShowtimeDate, StartTime) 
VALUES 
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 4, '2024-09-29', '10:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 4, '2024-09-29', '12:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 4, '2024-09-29', '15:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 4, '2024-09-29', '17:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 4, '2024-09-29', '19:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Làm Giàu Với Ma'), 4, '2024-09-29', '22:00');
go
-- Rạp 5
INSERT INTO Showtime (MovieID, CinemaRoomID, ShowtimeDate, StartTime) 
VALUES 
((SELECT MovieID FROM Movies WHERE Title = N'Tìm Kiếm Tài Năng Âm Phủ'), 5, '2024-09-29', '09:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Joker'), 5, '2024-09-29', '11:15'),
((SELECT MovieID FROM Movies WHERE Title = N'The Crow: Báo Thù'), 5, '2024-09-29', '13:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Báo Thủ Đi Tìm Chủ'), 5, '2024-09-29', '15:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Tìm Kiếm Tài Năng Âm Phủ'), 5, '2024-09-29', '18:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Joker'), 5, '2024-09-29', '20:15'),
((SELECT MovieID FROM Movies WHERE Title = N'The Crow: Báo Thù'), 5, '2024-09-29', '22:30');
go
-- Rạp 6
INSERT INTO Showtime (MovieID, CinemaRoomID, ShowtimeDate, StartTime) 
VALUES 
((SELECT MovieID FROM Movies WHERE Title = N'Không Nói Điều Dữ'), 6, '2024-09-29', '09:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Quỷ Án'), 6, '2024-09-29', '11:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Anh Trai Vượt Mọi Tam Tai'), 6, '2024-09-29', '14:00'),
((SELECT MovieID FROM Movies WHERE Title = N'Longlegs: Thảm Kịch Dị Giáo'), 6, '2024-09-29', '16:15'),
((SELECT MovieID FROM Movies WHERE Title = N'Không Nói Điều Dữ'), 6, '2024-09-29', '18:30'),
((SELECT MovieID FROM Movies WHERE Title = N'Quỷ Án'), 6, '2024-09-29', '20:45'),
((SELECT MovieID FROM Movies WHERE Title = N'Anh Trai Vượt Mọi Tam Tai'), 6, '2024-09-29', '23:00');
go


INSERT INTO SeatReservation (ShowtimeID, SeatID, Status) VALUES
(1, (SELECT TOP 1 SeatID FROM Seats WHERE ChairCode = 'A1'), 1), -- Ghế A1 đã đặt
(1, (SELECT TOP 1 SeatID FROM Seats WHERE ChairCode = 'B5'), 1), -- Ghế B5 đã đặt
(1, (SELECT TOP 1 SeatID FROM Seats WHERE ChairCode = 'C4'), 1), -- Ghế C4 đã đặt
(1, (SELECT TOP 1 SeatID FROM Seats WHERE ChairCode = 'A2'), 0), -- Ghế A2 chưa đặt
(1, (SELECT TOP 1 SeatID FROM Seats WHERE ChairCode = 'C2'), 0); -- Ghế C2 chưa đặt
go

-- Insert đánh giá cho Movie 1 (Làm Giàu Với Ma)
INSERT INTO Rate (MovieID, UserId, Content, Rating)
VALUES 
(1, 1, N'Phim rất hay và cảm động!', 1),
(1, 2, N'Cốt truyện hấp dẫn, diễn xuất tốt.', 1),
(1, 3, N'Tạm ổn, còn nhiều điểm thiếu sót.', 4),
(2, 1, N'Phim rất hay và cảm động!', 2),
(2, 2, N'Cốt truyện hấp dẫn, diễn xuất tốt.', 9),
(2, 3, N'Tạm ổn, còn nhiều điểm thiếu sót.', 5),
(3, 1, N'Phim rất hay và cảm động!', 9),
(3, 2, N'Cốt truyện hấp dẫn, diễn xuất tốt.', 7),
(3, 3, N'Tạm ổn, còn nhiều điểm thiếu sót.', 3),
(4, 1, N'Phim rất hay và cảm động!', 7),
(4, 2, N'Cốt truyện hấp dẫn, diễn xuất tốt.', 9),
(5, 3, N'Tạm ổn, còn nhiều điểm thiếu sót.', 6),
(5, 1, N'Phim rất hay và cảm động!', 2),
(5, 2, N'Cốt truyện hấp dẫn, diễn xuất tốt.', 7),
(6, 3, N'Tạm ổn, còn nhiều điểm thiếu sót.', 5),
(7, 1, N'Phim rất hay và cảm động!', 9),
(7, 2, N'Cốt truyện hấp dẫn, diễn xuất tốt.', 7),
(7, 3, N'Tạm ổn, còn nhiều điểm thiếu sót.', 9),
(7, 1, N'Phim rất hay và cảm động!', 8),
(8, 2, N'Cốt truyện hấp dẫn, diễn xuất tốt.', 6),
(8, 3, N'Tạm ổn, còn nhiều điểm thiếu sót.', 2);
go




INSERT INTO BuyTicket (BuyTicketId,UserId, MovieID)
VALUES (1,1, 1);
go
INSERT INTO BuyTicketInfo (BuyTicketId, Quantity, CreateDate, TotalPrice,ShowtimeID)
VALUES (1, 3, GETDATE(), 300.0,1);  -- Số lượng ghế là 3, giá vé là 300
go
INSERT INTO TicketSeat (BuyTicketId, SeatID)
VALUES (1, 14), (1, 15), (1, 16);
go
INSERT INTO SeatReservation (ShowtimeID, SeatID,Status)
VALUES (1,14,1),
 (1,15,1),
 (1,16,1);
go

/*
select * from BuyTicket
SELECT 
    bt.BuyTicketId, 
    bt.UserId, 
    bt.MovieID, 
	mv.Title,
    bti.Quantity, 
    bti.CreateDate, 
    bti.TotalPrice,
    STRING_AGG(ss.ChairCode, ', ') AS Seats,  -- Gộp các ChairCode thành một chuỗi
    st.StartTime AS StartTime  -- Lấy thời gian chiếu từ bảng Showtime
	
FROM 
    BuyTicket bt
JOIN 
    BuyTicketInfo bti ON bt.BuyTicketId = bti.BuyTicketId
JOIN 
    TicketSeat ts ON bt.BuyTicketId = ts.BuyTicketId
JOIN 
    Seats ss ON ts.SeatID = ss.SeatID  -- Lấy tên ghế từ bảng Seats
JOIN 
    Showtime st ON bti.ShowtimeID = st.ShowtimeID  -- Kết nối với bảng Showtime
JOIN Movies mv ON st.MovieID = mv.MovieID -- Kết nối với bảng Movie
GROUP BY 
    bt.BuyTicketId, 
    bt.UserId, 
    bt.MovieID, 
    bti.Quantity, 
    bti.CreateDate, 
    bti.TotalPrice,
    st.StartTime,	mv.Title;  -- Đảm bảo thêm st.Showtime vào GROUP BY



	*/

/*
SELECT * FROM Showtime WHERE MovieID =2


DECLARE @InputDate DATE = '2024-09-30';
DECLARE @InputTime TIME = '21:00';
DECLARE @movieID INT = 1;

SELECT 
    M.Title AS MovieTitle,
    M.Duration AS MovieDuration,  -- Lấy thêm thời lượng phim
    S.CinemaRoomID,
    S.ShowtimeDate,
    S.StartTime,
    DATEADD(MINUTE, M.Duration, CAST(S.StartTime AS DATETIME)) AS EndTime  -- Tính thời gian kết thúc
FROM 
    Showtime S
JOIN 
    Movies M ON S.MovieID = M.MovieID
WHERE 
    S.ShowtimeDate = @InputDate
    AND S.StartTime > @InputTime
    AND M.MovieID = @movieID
ORDER BY 
    S.StartTime;

        
 SELECT 
          s.SeatID,
                  s.CinemaRoomID,
          s.ChairCode,
                  s.DefectiveChair, 
                 
          COALESCE(sr.Status, 0) AS ReservationStatus
        FROM 
          Seats s
        LEFT JOIN 
          SeatReservation sr ON s.SeatID = sr.SeatID AND sr.ShowtimeID = 1
        WHERE 
          s.CinemaRoomID = 1 -- Lọc theo CinemaRoomID
        ORDER BY 
          s.SeatID;


                  ----new 23/09 5:43 ---

DECLARE @RoomID INT = 1;  -- RoomID của phòng chiếu bắt đầu
DECLARE @Row CHAR(1);  -- Hàng ghế (A, B, C,...)
DECLARE @SeatNumber INT;  -- Số ghế (1, 2, 3,...)

WHILE @RoomID <= 6  -- Giả sử có 6 phòng chiếu
BEGIN
    SET @Row = 'A';  -- Bắt đầu từ hàng 'A'
    
    -- Duyệt qua từng hàng từ 'A' đến 'M'
    WHILE ASCII(@Row) <= ASCII('M')  
    BEGIN
        SET @SeatNumber = 1;  -- Đặt lại số ghế bắt đầu từ 1
        
        -- Duyệt qua từng ghế từ 1 đến 16 trong mỗi hàng
        WHILE @SeatNumber <= 16  
        BEGIN
            -- Xác định mã ghế theo hàng và số ghế (ví dụ A1, A2,... M16,...)
            INSERT INTO Seats (CinemaRoomID, ChairCode, Status)
            VALUES (@RoomID, @Row + CAST(@SeatNumber AS NVARCHAR(10)), 0);
            
            -- Tăng số ghế lên
            SET @SeatNumber = @SeatNumber + 1;
        END
        
        -- Chuyển sang hàng tiếp theo (A -> B -> C, v.v.)
        SET @Row = CHAR(ASCII(@Row) + 1);  
    END
    
    -- Chuyển sang phòng chiếu tiếp theo
    SET @RoomID = @RoomID + 1;
END;
GO
        */











/*
-- Insert dữ liệu cho bảng Favourite
INSERT INTO Favourite (MovieID, UserId)
VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

-- Insert dữ liệu cho bảng BuyTicket
INSERT INTO BuyTicket (UserId, MovieID)
VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

-- Insert dữ liệu cho bảng BuyTicketInfo
INSERT INTO BuyTicketInfo (BuyTicketId, Quantity, CreateDate, TotalPrice, ComboID)
VALUES 
(1, 2, GETDATE(), 200.00, NULL),
(2, 3, GETDATE(), 300.00, NULL),
(3, 1, GETDATE(), 100.00, NULL),
(4, 4, GETDATE(), 400.00, NULL),
(5, 5, GETDATE(), 500.00, NULL);

-- Insert dữ liệu cho bảng ComBo
INSERT INTO ComBo (BuyTicketInfoId, Quantity, Price)
VALUES 
( 1, 50.00),
( 2, 100.00),
( 1, 50.00),
( 2, 100.00),
( 3, 150.00);

-- Insert dữ liệu cho bảng Ticket
INSERT INTO Ticket (BuyTicketId, Price, ChairCode, CinemaRoomID)
VALUES 
(1, 100.00, N'A1', 1),
(2, 200.00, N'B2', 2),
(3, 150.00, N'C3', 3),
(4, 180.00, N'D4', 4),
(5, 220.00, N'E5', 5);
*/













/*
-------------- SOCKET IO ---------------------
CREATE TABLE Conversations (
    Id INT PRIMARY KEY IDENTITY(1,1),
    User1Id INT NOT NULL,
    User2Id INT NOT NULL,
    StartedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (User1Id) REFERENCES Users(UserId) ON DELETE NO ACTION,
    FOREIGN KEY (User2Id) REFERENCES Users(UserId) ON DELETE NO ACTION
);
go

CREATE TABLE Messages (
    Id INT PRIMARY KEY IDENTITY(1,1),
    SenderId INT NOT NULL,
    ReceiverId INT NOT NULL,
    Message NVARCHAR(255) NOT NULL,
    SentAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    ConversationId INT NOT NULL,
    FOREIGN KEY (SenderId) REFERENCES Users(UserId) ON DELETE NO ACTION,
    FOREIGN KEY (ReceiverId) REFERENCES Users(UserId) ON DELETE NO ACTION,
    FOREIGN KEY (ConversationId) REFERENCES Conversations(Id) ON DELETE CASCADE
);
go

 SELECT 
    m.MovieID,
    m.Title,
    m.Description,
    m.Duration,
    m.ReleaseDate,
    m.PosterUrl,
    m.TrailerUrl,
    l.LanguageName,
    STRING_AGG(g.GenreName, ', ') AS Genres, -- Kết hợp các thể loại thành một chuỗi
    c.CinemaName,
    c.Address AS CinemaAddress,
    STRING_AGG(r.Content, ' | ') AS ReviewContents, -- Kết hợp các đánh giá thành một chuỗi
    AVG(r.Rating) AS AverageRating, -- Tính điểm đánh giá trung bình
    COUNT(r.IdRate) AS ReviewCount -- Đếm số lượng đánh giá
FROM 
    Movies m
LEFT JOIN 
    Language l ON m.IdLanguage = l.IdLanguage
LEFT JOIN 
    MovieGenre mg ON m.MovieID = mg.MovieID
LEFT JOIN 
    Genre g ON mg.IdGenre = g.IdGenre
LEFT JOIN 
    Cinemas c ON m.CinemaID = c.CinemaID
        */




