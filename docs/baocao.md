CHÚ Ý:
Mỗi nhóm  soạn thảo báo cáo trực tiếp vào tệp văn bản (docx, edit online bằng Google Docs) theo cấu trúc dưới đây, đặt trong thư mục online của nhóm và đó. 
Quy tắc trình bày quyển báo cáo phải tuân thủ Quy định của khoa
Báo cáo môn này được phép in 2 mặt (do số trang rất nhiều)
Yêu cầu về Bài toán
Đa người dùng.
Đa thiết bị/phương tiện (máy tính, điện thoại, máy in...).
Có nghiệp vụ (yêu cầu, quy trình, mẫu biểu) thực tế.
Sử dụng UML để mô hình hoá các kết quả khảo sát, phân tích, thiết kế
Sử dụng Git (hoặc công cụ tương đương khác) để quản lý mã nguồn của dự án
Mọi nhận định trong báo cáo này phải được chứng minh hoặc dẫn nguồn nếu trích từ tài liệu khác (tài liệu đó phải được liệt kê trong phần TÀI LIỆU THAM KHẢO)
Mọi bảng biểu (Table) phải được thiết lập để tiêu đề in lặp lại ở trang thứ 2 trở đi (nếu bảng trải dài nhiều trang)
Tài liệu tham khảo được chấp nhận gồm:
Sách về CNPM 
Bài báo khoa học về CNPM
Các chuẩn đã được công bố về CNPM
Mẫu báo cáo này được cập nhật lần cuối ngày 16/09/2024


BÌA BÁO CÁO 
LỜI NÓI ĐẦU
...
MỤC LỤC
...
CÁC DANH MỤC
...
CHƯƠNG 1. GIỚI THIỆU
Tổng quan về đề tài
Trong bối cảnh công nghệ thông tin ngày càng phát triển mạnh mẽ, việc ứng dụng phần mềm vào công tác quản lý trong lĩnh vực giáo dục đã trở thành một nhu cầu tất yếu. Công tác quản lý học sinh tại các trường học hiện nay vẫn còn tồn tại nhiều hạn chế nếu thực hiện thủ công hoặc bằng các công cụ rời rạc như sổ sách, bảng tính, gây khó khăn trong việc lưu trữ, tra cứu, cập nhật thông tin và tổng hợp báo cáo.
Xuất phát từ thực tế đó, đề tài “Phát triển phần mềm quản lý học sinh của một trường” được lựa chọn nhằm xây dựng một hệ thống phần mềm hỗ trợ hiệu quả cho công tác quản lý học sinh. Phần mềm cho phép quản lý tập trung các thông tin liên quan đến học sinh như hồ sơ cá nhân, lớp học, kết quả học tập, cũng như hỗ trợ các nghiệp vụ quản lý và báo cáo một cách nhanh chóng, chính xác.
Hệ thống được thiết kế hướng tới nhiều nhóm người dùng khác nhau như quản trị viên, giáo viên và giáo vụ, đảm bảo đáp ứng yêu cầu làm việc đa người dùng. Bên cạnh đó, phần mềm có khả năng hoạt động trên nhiều thiết bị như máy tính và điện thoại thông minh thông qua giao diện thân thiện, dễ sử dụng.
Việc thực hiện đề tài không chỉ giúp sinh viên vận dụng các kiến thức đã học trong học phần Nhập môn Công nghệ Phần mềm như phân tích yêu cầu, thiết kế hệ thống, quản lý mã nguồn bằng Git, mà còn giúp làm quen với quy trình phát triển phần mềm trong thực tế. Qua đó, đề tài góp phần nâng cao kỹ năng làm việc nhóm, tư duy hệ thống và khả năng xây dựng phần mềm ứng dụng phục vụ nhu cầu thực tiễn.
Giới thiệu bài toán
1.2.1. Thực trạng (Vấn đề hiện tại)
Qua khảo sát quy trình quản lý tại văn phòng nhà trường và các tổ chuyên môn, hiện trạng quản lý đang gặp các vấn đề sau:
Quản lý thủ công, rời rạc: Điểm số (miệng, 15 phút, 1 tiết, thi học kỳ) thường được giáo viên bộ môn ghi chép vào sổ tay cá nhân hoặc file Excel riêng lẻ, sau đó mới cập nhật vào sổ cái. Quy trình này dễ dẫn đến sai sót khi tính toán điểm trung bình môn và xếp loại học lực.
Thiếu kênh thông tin tức thời: Phụ huynh muốn biết điểm số hay tình hình đi học của con em thường phải đợi đến kỳ họp phụ huynh hoặc sổ liên lạc giấy cuối tháng, dẫn đến việc phối hợp giáo dục giữa gia đình và nhà trường bị chậm trễ.
1.2.2. Yêu cầu giải pháp (Hệ thống GtuSchool)
Hệ thống phần mềm quản lý học sinh GtuSchool cần giải quyết được các bài toán trên thông qua các yêu cầu kỹ thuật cụ thể:
Về nền tảng: Xây dựng ứng dụng Web (Web-based) hoạt động đa nền tảng:
Trên Máy tính (PC): Dành cho Giáo viên/Giáo vụ để nhập liệu điểm số hàng loạt, xếp thời khóa biểu và in ấn các mẫu báo cáo thống kê (Danh sách lớp, Bảng điểm tổng hợp).
Trên Điện thoại (Mobile): Dành cho Phụ huynh để tra cứu thời khóa biểu, điểm số, nhận thông báo chuyên cần của con em mình mọi lúc mọi nơi.
Về người dùng: Hệ thống phân quyền chi tiết cho các nhóm đối tượng: Admin (Ban giám hiệu) - Giáo vụ - Giáo viên chủ nhiệm/Bộ môn - Phụ huynh/Học sinh.
Về quản lý mã nguồn: Áp dụng quy trình quản lý phiên bản mã nguồn bằng Git để đảm bảo an toàn dữ liệu và hỗ trợ làm việc nhóm hiệu quả trong quá trình phát triển dự án.
Mục tiêu của đề tài
Mục tiêu của đề tài không chỉ dừng lại ở việc tạo ra một phần mềm chạy được, mà quan trọng hơn là việc rèn luyện tư duy và áp dụng quy trình sản xuất phần mềm chuyên nghiệp. Đề tài hướng tới hai nhóm mục tiêu chính:
1.3.1. Mục tiêu về sản phẩm
Xây dựng thành công hệ thống GtuSchool giúp tin học hóa công tác quản lý tại trường THPT GTU:
Số hóa hồ sơ: Chuyển đổi toàn bộ hồ sơ học sinh (lý lịch, quá trình học tập từ lớp 10-12) và sổ sách chuyên môn (sổ điểm cái, sổ đầu bài) sang dạng dữ liệu số tập trung.
Tự động hóa tính toán: Hệ thống tự động tính điểm trung bình môn, điểm tổng kết học kỳ và xếp loại học lực, hạnh kiểm theo đúng quy chế của Bộ GD&ĐT (Thông tư 58/22), loại bỏ hoàn toàn sai sót do tính toán thủ công.
Kết nối Gia đình - Nhà trường: Cung cấp tính năng "Sổ liên lạc điện tử" trên nền tảng Web/Mobile, giúp Phụ huynh theo dõi điểm số và chuyên cần của con em mình theo thời gian thực.


1.3.2. Mục tiêu về quy trình kỹ thuật
Đây là mục tiêu cốt lõi để đáp ứng yêu cầu môn học, chuyển dịch từ thói quen "lập trình tự do" (coding-first) sang quy trình phát triển phần mềm bài bản (engineering-oriented):
Tuân thủ quy trình phát triển phần mềm (SDLC):
Nhóm thực hiện cam kết không viết mã lệnh (code) ngay lập tức.
Dự án phải tuân thủ nghiêm ngặt trình tự: Khảo sát →  Phân tích (Vẽ Use Case, Quy trình nghiệp vụ) → Thiết kế (Kiến trúc, CSDL, Giao diện) →Cài đặt →Kiểm thử.
Quản lý cấu hình và làm việc nhóm (Configuration Management):
Sử dụng Git để quản lý phiên bản mã nguồn, giải quyết bài toán xung đột code (conflict) khi làm việc nhóm, thay vì chia sẻ mã nguồn thủ công qua USB hay Drive như trước đây.
Áp dụng kiến trúc chuẩn mực:
Hệ thống được thiết kế tách biệt rõ ràng giữa Giao diện (Frontend) và Xử lý nghiệp vụ (Backend) thông qua API, đảm bảo tính bảo mật và dễ dàng bảo trì, nâng cấp.
Đáp ứng tiêu chuẩn Đa nền tảng (Cross-platform) và Đa người dùng (Multi-user) thông qua thiết kế Web Responsive và cơ chế phân quyền RBAC (Role-Based Access Control).
Các đề tài/hệ thống liên quan 
1.4.1. Hệ thống Mạng giáo dục Việt Nam - vnEdu (VNPT)
Đây là một trong những hệ thống quản lý giáo dục phổ biến nhất hiện nay, được phát triển bởi Tập đoàn VNPT và triển khai tại hàng nghìn trường học trên cả nước.

Giao diện bảng điểm của hệ thống vnEdu (Nguồn: vnedu.vn)
Đánh giá ưu điểm:
Tính năng đồ sộ: Bao phủ hầu hết các nghiệp vụ từ Mầm non đến THPT (Quản lý hồ sơ, điểm, thời khóa biểu, kỳ thi, thư viện...).
Hệ sinh thái: Tích hợp tốt với ứng dụng vnEdu Connect giúp phụ huynh tra cứu điểm số và nhận thông báo trên điện thoại.
Chuẩn hóa: Dữ liệu đồng bộ với cơ sở dữ liệu quốc gia của Bộ GD&ĐT.
Đánh giá nhược điểm:
Giao diện phức tạp: Do tích hợp quá nhiều phân hệ nên giao diện của vnEdu thường bị đánh giá là rối rắm, nhiều thao tác thừa, gây khó khăn cho giáo viên lớn tuổi khi mới tiếp cận.
Chi phí cao: Chi phí triển khai và duy trì tin nhắn sổ liên lạc điện tử là rào cản đối với các trường tư thục quy mô nhỏ hoặc các dự án mang tính nội bộ.
1.4.2. Hệ thống Quản lý nhà trường - SMAS (Viettel)
Hệ thống SMAS (School Management System) do Tập đoàn Viettel phát triển, là đối thủ cạnh tranh trực tiếp của vnEdu, tập trung mạnh vào việc số hóa các sổ sách báo cáo.

Giao diện quản lý hồ sơ học sinh trên SMAS (Nguồn: smas.edu.vn)
Đánh giá ưu điểm:
Hạ tầng mạnh: Tốc độ truy cập ổn định nhờ hạ tầng máy chủ của Viettel.
Báo cáo đa dạng: Hỗ trợ xuất ra rất nhiều mẫu báo cáo thống kê theo đúng chuẩn của từng Sở GD&ĐT địa phương.
Đánh giá nhược điểm:
Khả năng tùy biến thấp: Hệ thống được thiết kế "đóng gói" cứng (Hard-coded logic) cho quy trình chung, rất khó để tùy chỉnh theo quy trình đặc thù riêng của một trường cụ thể (ví dụ: quy tắc cộng điểm thi đua riêng của trường GTU).
Trải nghiệm người dùng (UX): Giao diện Web trên thiết bị di động (Mobile Web) chưa thực sự mượt mà, chủ yếu vẫn tối ưu cho thao tác trên máy tính bàn
Phương pháp phát triển
Lựa chọn phương pháp phát triển
Sau khi xem xét đề tài “Phát triển phần mềm quản lý học sinh cho trường GTUSchool”, nhóm đưa ra ba mô hình phát triển có thể xem xét là Waterfall, Agile và V-Model.
Mô hình Waterfall

Mô tả:
Tiến trình tuyến tính: Hoạt động diễn ra tuần tự từ trên xuống dưới theo một chiều duy nhất (Khảo sát -> Phân tích -> Thiết kế -> Cài đặt -> Kiểm thử -> Triển khai).
Nguyên tắc "đóng băng" (Freeze phase): Một giai đoạn mới chỉ được bắt đầu khi giai đoạn trước đó đã hoàn tất 100%, được nghiệm thu và đóng gói thành tài liệu đặc tả.
Hạn chế quay lui: Rất khó và tốn kém để quay ngược lại các bước trước đó nhằm sửa đổi thiết kế khi hệ thống đã bước vào pha lập trình hoặc kiểm thử.

Ứng dụng: 
Các dự án có yêu cầu nghiệp vụ tĩnh, được định nghĩa cực kỳ rõ ràng ngay từ ngày đầu tiên.
Các hệ thống có công nghệ không thay đổi và quy mô rủi ro kỹ thuật thấp.
Các dự án chính phủ hoặc doanh nghiệp truyền thống đòi hỏi hệ thống tài liệu đặc tả kỹ thuật khổng lồ, nghiêm ngặt từ đầu đến cuối.
Mô hình Agile

Mô tả: 
Phát triển lặp và tăng dần: Dự án không chạy một mạch mà được chia nhỏ thành các chu kỳ phát triển cực ngắn (Sprint, thường kéo dài từ 2-4 tuần).
Ưu tiên thực tiễn hơn tài liệu: Tập trung vào việc sớm tạo ra các tính năng chạy được (working software) để khách hàng dùng thử, thay vì viết các bộ tài liệu thiết kế đồ sộ.
Đón nhận thay đổi: Yêu cầu rất linh hoạt, các tính năng có thể được thêm, bớt hoặc thay đổi thứ tự ưu tiên ở bất kỳ giai đoạn nào của dự án.

Ứng dụng: 
Tuyệt vời cho môi trường startup hoặc các sản phẩm công nghệ mà yêu cầu liên tục thay đổi theo thị hiếu của thị trường.
Các dự án có khách hàng sẵn sàng tham gia sâu, họp bàn và phản hồi hàng tuần với đội ngũ phát triển.
Đòi hỏi đội ngũ kỹ sư phải có kinh nghiệm thực chiến và kỹ năng tự quản lý chéo cực kỳ cao.
Mô hình V-model

Mô tả: 
Cấu trúc song hành: Là bản nâng cấp của Waterfall theo hình chữ "V". Nhánh bên trái là Xác minh (Phân tích, Thiết kế, Cài đặt), nhánh bên phải là Xác thực (Các cấp độ Kiểm thử).
Kiểm thử từ sớm: Cứ mỗi khi một tài liệu thiết kế ở nhánh trái được lập ra (VD: Thiết kế cơ sở dữ liệu), ngay lập tức một Kế hoạch kiểm thử tương ứng ở nhánh phải cũng được soạn thảo.
Kiểm soát chất lượng liên tục: Việc chuẩn bị kịch bản bắt lỗi được tiến hành ngay từ ngày đầu tiên thay vì đẩy toàn bộ công việc kiểm thử xuống cuối dự án.

Ứng dụng: 
Các dự án vừa và nhỏ có thời gian, ngân sách và yêu cầu đã được chốt chặt chẽ.
Ứng dụng mạnh mẽ trong các hệ thống đòi hỏi độ tin cậy cao, cần kiểm soát lỗi cấu trúc khắt khe (VD: phần mềm y tế, hệ thống tính toán điểm số/tài chính).
Đòi hỏi đội ngũ phát triển và kiểm thử viên (Tester) phải phối hợp nhịp nhàng ở mọi giai đoạn.
Kết luận
Dựa trên việc đối chiếu hoàn cảnh thực tiễn của dự án GtuSchool với đặc điểm ứng dụng của các mô hình trên, nhóm quyết định lựa chọn V-Model làm phương pháp luận cốt lõi vì các lý do sau:
Tương thích hoàn toàn với tính chất dự án: Nghiệp vụ quản lý học sinh theo quy chế đã được chuẩn hóa. Yêu cầu hệ thống (FR, NFR) tĩnh và ít biến động. Quy mô dự án nhỏ và thời gian thực hiện ngắn (< 3 tháng). Đây chính là môi trường ứng dụng lý tưởng nhất của V-Model.
Đảm bảo chất lượng theo tiêu chuẩn học thuật: Đặc thù môn học Nhập môn CNPM yêu cầu xây dựng hệ thống tài liệu và thiết kế (SRS, UML) rất khắt khe. V-Model không những đáp ứng được việc này, mà cơ chế "lập kịch bản kiểm thử song hành với thiết kế" còn giúp nhóm tự kiểm tra logic tài liệu của mình ngay từ sớm, tránh sai sót.




Vai trò của thành viên dự án
STT
Họ và tên
rAI
DT
1
Từ Đức Anh


x
2
Đặng Quang Minh


x
3
Lê Quang Minh


x
4
Nguyễn Thành Đạt
x


5
Trần Văn Dũng
x


6
Nguyễn Hoàng Quốc Duy
x


7
Tạ Danh Phong
x





Phân công nhiệm vụ
STT
Họ và tên
Vai trò
Nhiệm vụ
01
Từ Đức Anh
DT
Thiết kế kiến trúc, CSDL
02
Đặng Quang Minh
DT
Thiết kế giao diện, API
03
Lê Quang Minh
DT
Thiết kế testcase, quy trình test
04
Nguyễn Thành Đạt
rAI
Quản lý Hồ sơ học sinh
05
Trần Văn Dũng
rAI
Quản lý Lớp & thời khóa biểu
06
Nguyễn Hoàng Quốc Duy
rAI
Quản lý Điểm & học lực
07
Tạ Danh Phong
rAI
Quản lý Tài khoản & phân quyền



Kế hoạch triển khai chi tiết 
STT 
Công việc 
Yêu cầu  
Ngày bắt đầu 
Ngày kết thúc 
Cách thực hiện 
1 
Phân tích yêu cầu
Xác định đầy đủ yêu cầu chức năng và phi chức năng của hệ thống




Phỏng vấn giả lập người dùng (BGH, giáo viên), xây dựng Use Case, lập tài liệu SRS
2 
Thiết kế hệ thống
Thiết kế kiến trúc tổng thể và CSDL




Xây dựng kiến trúc 3-tier, vẽ ERD, Class Diagram, Sequence Diagram
3 
Thiết kế chi tiết
Thiết kế module, thuật toán xử lý nghiệp vụ




Thiết kế chức năng từng module (quản lý học sinh, điểm, học bạ…), viết đặc tả chi tiết
4 
Lập kế hoạch kiểm thử
Xây dựng test case cho từng mức kiểm thử




Viết Test Plan, Test Case cho Unit, Integration, System, Acceptance
5 
Coding
Triển khai lập trình hệ thống




Lập trình theo module, xây dựng database, tích hợp giao diện và backend
6 
Unit Test
Kiểm thử từng hàm/module riêng lẻ




Viết test case, kiểm thử các hàm xử lý dữ liệu, sửa lỗi phát sinh
7 
Integration Test
Kiểm thử khi tích hợp các module




Kiểm thử luồng nghiệp vụ hoàn chỉnh (thêm HS → nhập điểm → tính học lực)
8 
System Test
Kiểm thử toàn bộ hệ thống




Kiểm thử chức năng, phân quyền, bảo mật, hiệu năng
9 
Acceptance Test
Kiểm thử nghiệm thu với người dùng




Người dùng giả lập kiểm tra theo SRS, ghi nhận phản hồi
10
Hoàn thiện & Báo cáo
Hoàn thiện tài liệu và sản phẩm




Sửa lỗi cuối, hoàn chỉnh tài liệu, chuẩn bị slide bảo vệ

 


CHƯƠNG 2. SẢN PHẨM CHUYỂN GIAO GIỮA CÁC PHA 
VÀ TIÊU CHUẨN ĐÁNH GIÁ
Danh mục tài liệu, sản phẩm
STT
Loại tài liệu/Sản phẩm
Biểu mẫu
Tiêu chuẩn đánh giá
Pha R
Pha A
Pha D
Pha I
Pha T
1
Báo cáo khảo sát hiện trạng
G03_Hồ sơ khảo sát
Đánh giá theo ISO/IEC/IEEE 15289:2011 – Information Item phải có: Purpose, Scope, Content, Source, Acceptance Criteria. Nội dung phải làm cơ sở hình thành Stakeholder Requirements theo ISO/IEC/IEEE 29148:2011.
Out
In






2
Xác định cơ cấu tổ chức
G03_Cơ cấu tổ chức
Phải xác định Stakeholders và Organizational Context theo ISO/IEC/IEEE 29148:2011. Thông tin phải rõ nguồn gốc (traceable), nhất quán và làm cơ sở cho phân quyền hệ thống.
Out
In






3
Danh sách yêu cầu hệ thống (FR & NR)
G03_FR & NFR
Mỗi yêu cầu phải đáp ứng các thuộc tính chất lượng theo ISO/IEC/IEEE 29148:2011: Necessary, Correct, Unambiguous, Complete, Singular, Feasible, Verifiable, Traceable, Implementation-free, Conforming.
Out
In
In
In


4
Mô hình hóa quy trình nghiệp vụ
G03_Quy trình nghiệp vụ
Phải hỗ trợ mô tả kiến trúc và quan điểm (Viewpoint) theo ISO/IEC/IEEE 42010:2011. Các mô hình phải truy vết được tới yêu cầu và hỗ trợ Verification & Validation.
Out






In
5
Đặc tả yêu cầu phần mềm (SRS)
G03_SRS
Phải tuân thủ cấu trúc SRS theo ISO/IEC/IEEE 29148:2011. Yêu cầu phi chức năng phải dựa trên mô hình chất lượng ISO/IEC 25010:2011 (8 đặc tính chất lượng). Tài liệu phải truy vết được tới Stakeholder Requirements và Test Case.
Out






In
6
Sơ đồ phân rã chức năng
G03_Mô hình hoá chức năng
Xác định phạm vi của hệ thống cần phân tích, biểu diễn chức năng trực tiếp để tìm ra các chức năng thiếu hoặc trùng lặp.


Out
In
In
In
7
Đặc tả các chức năng mức lá
Đặc tả chi tiết từng chức năng mức lá được đề cập ở sơ đồ phân rã chức năng


Out
In
In
In
8
Thiết kế Cơ sở dữ liệu
G03_Thiết kế CSDL
Sơ đồ ERD được chuẩn hóa tối thiểu đạt mức 3NF hoặc BCNF. Đảm bảo quy tắc đặt tên bảng/cột thống nhất.




Out
In


9
Đặc tả CSDL
Đặc tả toàn bộ CSDL đã đưa ra từ bước thiết kế CSDL


Out
In




10
Bản thiết kế đặc tả
G03_Thiết kế
Mô hình thiết kế tổng quan; đưa ra được khảo sát thiết kế mô hình




Out
In
In
11
Bản thiết kế chi tiết
Mô hình thiết kế chi tiết từ bản thiết kế đặc tả




Out
In
In
12
Source Code (Mã nguồn phần mềm)
G03_Mã nguồn
Code sạch (Clean code), xử lý nghiệp vụ đúng logic, ít lỗi tiềm ẩn và được quản lý phiên bản tốt (qua Github).






Out
In
13
Kế hoạch & Kịch bản kiểm thử (Test Plan/Cases)
G03_Testing
Tất cả các test case đều được thực hiện, đưa ra kết luận về nguyên nhân lỗi, được sự thông qua của nhóm trưởng [4] (BABOK V3 Hướng Dẫn Kiến Thức Cốt Lõi Về Phân Tích Nghiệp Vụ, 2015, #)








Out
14
Kết quả kiểm thử chức năng








Out
15
Báo cáo kiểm thử
G03_Báo cáo kiểm thử 
Báo cáo tổng hợp quá trình kiểm thử








Out

Danh mục tài liệu, sản phẩm Quy tắc đặt tên (naming convention)
2.2.1. Quy tắc đặt tên dự án
STT
Loại
Quy tắc
VD
1
Tên Folder, Namespace, File, Class, Method
PascalCase (Viết hoa chữ cái đầu mỗi từ)
QuanLyHocSinh, CalculateScore()
2
Hằng số (Constant)
SCREAMING_SNAKE_CASE (Viết hoa toàn bộ, cách nhau bằng gạch dưới)
MAX_STUDENT_PER_CLASS, DEFAULT_ROLE
3
Biến (Variable)
camelCase (Viết thường chữ cái đầu tiên, viết hoa chữ cái đầu các từ tiếp theo)
studentName, totalScore

2.2.2. Quy Tắc đặt tên Database
STT
Loại
Tiền tố (Quy tắc)
VD
1
Database
db
dbGtuSchool
2
Table
tbl
tblHocSinh, tblDiemSo
3
Int
i
iSoThuTu, iTuoi
4
Float
f
fDiemTrungBinh
5
Nvarchar
s
sHoTen, sDiaChi
6
Varchar
s
sEmail, sPassword
7
char
s
sGioiTinh
8
text
s
sGhiChu
9
Date/Datetime
d
dNgaySinh, dNgayNhapHoc
10
bit
b
bTrangThai (True/False)
11
Primary Key
Pk
Pk_iMaHS
12
Foreign Key
Fk
Fk_iMaLop


Xác định Mục tiêu chất lượng của dự án
STT
Tên độ đo
Ý nghĩa
Công thức/cách đo
1
Độ chính xác
Kiểm tra tính chính xác của hệ thống khi xử lý dữ liệu và tính toán
So sánh kết quả đầu ra của hệ thống với giá trị mong đợi
2
Hiệu suất
Đánh giá tốc độ phản hồi của hệ thống
Đo thời gian từ khi gửi yêu cầu đến khi nhận phản hồi
3
Khả năng chịu tải
Hệ thống có thể xử lý nhiều người dùng đồng thời
Sử dụng stress test để kiểm tra mức tải tối đa
4
Tính ổn định
Hệ thống có thể xử lý nhiều người dùng đồng thời
Theo dõi thời gian uptime qua công cụ giám sát
5
Bảo mật
Đảm bảo dữ liệu người dùng được bảo vệ
Thực hiện kiểm thử bảo mật (penetration testing)
6
Tính dễ sử dụng
Người dùng có thể dễ dàng thao tác với hệ thống
Khảo sát ý kiến người dùng
7
Tính bảo trì
Hệ thống dễ dàng cập nhật và sửa lỗi
Đo thời gian trung bình để khắc phục lỗi (MTTR)
8
Khả năng mở rộng
Dễ dàng thêm tính năng mới mà không ảnh hưởng hệ thống cũ
Đánh giá khả năng tích hợp module mới


Xác định tiêu chuẩn Kiểm thử được (Testability) cho dự án
Khái niệm, ý nghĩa của đặc tính Kiểm thử được
Theo tiêu chuẩn ISO/IEC 25010:2011: “Testability - degree of effectiveness and efficiency with which test criteria can be established for a system, product or component and tests can be performed to determine whether those criteria have been met”
(Testability - mức độ hiệu quả và hiệu suất mà các tiêu chí thử nghiệm có thể được thiết lập cho một hệ thống, sản phẩm hoặc thành phần và các thử nghiệm có thể được thực hiện để xác định xem các tiêu chí đó có được đáp ứng hay không)

Chi tiết hóa các tiêu chí của đặc tính Kiểm thử được
STT
Tiêu chí
Yêu cầu cụ thể đối với dự án
1
Tính quan sát được (Observability)
(Hệ thống cần cung cấp thông tin hữu ích về trạng thái và hành vi của nó trong quá trình kiểm thử.)
- Hệ thống phải ghi lại các log chi tiết cho mỗi sự kiện và hành động.
- Cung cấp thông báo trạng thái và lỗi trực quan để kiểm thử viên biết rõ vấn đề xảy ra ở đâu trong hệ thống.
2
Tính điều khiển được (Controllability)
(Người kiểm thử có thể dễ dàng điều khiển và thay đổi đầu vào của hệ thống để kiểm tra các hành vi khác nhau.)
- Giao diện nhập liệu cho phép dễ dàng thiết lập đầu vào.
- Có khả năng mô phỏng các điều kiện như mất kết nối hoặc dữ liệu không hợp lệ để thử nghiệm cách hệ thống xử lý.
3
Khả năng phân lập (Isolation)
(Khả năng kiểm thử các thành phần riêng biệt mà không cần chạy toàn bộ hệ thống.)
- Tạo ra các module độc lập để kiểm thử đơn vị, không cần phụ thuộc vào toàn bộ hệ thống.
- Sử dụng Mock objects hoặc Stub để kiểm tra các chức năng giao tiếp giữa các module.
4
Tính tái tạo lỗi (Reproducibility)
(Khả năng tái tạo lỗi một cách chính xác với cùng điều kiện đầu vào.)
- Cần cung cấp môi trường ổn định để kiểm thử có thể tái hiện lỗi.
- Ghi lại các điều kiện đầu vào chi tiết để dễ dàng tái tạo tình huống gây lỗi.
5
Tính tự động hóa kiểm thử (Automatability)
(Khả năng thực hiện kiểm thử tự động mà không cần can thiệp thủ công.)
- Phải hỗ trợ các công cụ kiểm thử tự động và tích hợp vào quy trình Continuous Integration (CI).
- Các API cần được thiết kế để tự động hóa các kiểm thử chức năng chính.
6
Tính dễ hiểu (Understandability)
(Hệ thống phải dễ hiểu để người kiểm thử có thể nắm rõ cấu trúc và hành vi của nó.)
- Cung cấp tài liệu chi tiết về cấu trúc hệ thống và các chức năng.
- Mã nguồn phải tuân thủ tiêu chuẩn lập trình và dễ đọc để giảm thời gian hiểu và kiểm tra.
7
Tính theo dõi (Traceability)
(Khả năng liên kết các yêu cầu với các trường hợp kiểm thử để đảm bảo kiểm thử đầy đủ.)
- Mỗi yêu cầu hệ thống phải có test case tương ứng.
- Hệ thống cần báo cáo chi tiết trạng thái của mỗi yêu cầu đã được kiểm thử (pass/fail) để theo dõi tiến độ kiểm thử.

CHƯƠNG 3. XÁC ĐỊNH YÊU CẦU
Vai trò của việc Xác định yêu cầu đối với dự án phần mềm
Xác định yêu cầu (Requirements Engineering) là một trong những giai đoạn quan trọng nhất trong vòng đời phát triển phần mềm. Giai đoạn này đóng vai trò nền tảng, quyết định đến sự thành công của toàn bộ dự án.
Cụ thể, việc xác định yêu cầu có các vai trò sau:
Làm rõ nhu cầu của stakeholders:
 Giúp thu thập, phân tích và thống nhất các yêu cầu từ phía người dùng, nhà trường và các bên liên quan.
Làm cơ sở cho thiết kế và phát triển:
 Tất cả các hoạt động thiết kế hệ thống, lập trình và kiểm thử đều dựa trên yêu cầu đã xác định.
Giảm thiểu rủi ro:
 Phát hiện sớm các yêu cầu mâu thuẫn, thiếu sót hoặc không khả thi, từ đó giảm chi phí sửa lỗi về sau.
Hỗ trợ kiểm thử và nghiệm thu:
 Yêu cầu là tiêu chí để xây dựng test case và đánh giá hệ thống có đáp ứng đúng hay không.
Tăng khả năng quản lý dự án:
 Giúp theo dõi tiến độ, kiểm soát phạm vi và tránh phát sinh yêu cầu không cần thiết.
Phương pháp đánh giá chất lượng của yêu cầu
Để đảm bảo các yêu cầu được xác định là đúng và có thể triển khai, nhóm sử dụng bộ tiêu chí chất lượng yêu cầu dựa trên chuẩn IEEE, bao gồm:
Necessary (Cần thiết):
 Yêu cầu phải thực sự cần cho hệ thống.
Correct (Đúng đắn):
 Phản ánh đúng nhu cầu thực tế của người dùng.
Unambiguous (Không mơ hồ):
 Mỗi yêu cầu chỉ có một cách hiểu duy nhất.
Complete (Đầy đủ):
 Không thiếu thông tin cần thiết.
Singular (Đơn nhất):
 Mỗi yêu cầu chỉ mô tả một chức năng.
Feasible (Khả thi):
 Có thể thực hiện với công nghệ và nguồn lực hiện có.
Verifiable (Có thể kiểm chứng):
 Có thể kiểm tra bằng test case.
Traceable (Truy vết được):
 Liên kết với các yêu cầu khác hoặc mục tiêu hệ thống.
Implementation-free (Không phụ thuộc cài đặt):
 Không mô tả chi tiết kỹ thuật triển khai.
Conforming (Tuân thủ chuẩn):
 Tuân theo format và quy định tài liệu SRS.
Danh sách các yêu cầu của hệ thống
3.3.1 Yêu cầu chức năng (Functional Requirements – FR)
Hệ thống có tổng cộng 60 yêu cầu chức năng, được chia thành các nhóm chính:
Quản lý tài khoản và phân quyền (FR-01 → FR-09)
Quản lý hồ sơ học sinh (FR-10 → FR-14)
Quản lý lớp học (FR-15 → FR-19)
Quản lý điểm số và học lực (FR-20 → FR-26)
Quản lý hạnh kiểm (FR-27 → FR-28)
Quản lý năm học (FR-29 → FR-33)
Tra cứu thông tin (FR-34 → FR-37)
Quản lý thời khóa biểu (FR-38 → FR-42)
Quản lý môn học (FR-43 → FR-45)
Phân công giảng dạy (FR-46 → FR-49)
Báo cáo và thống kê (FR-50 → FR-54)
Hệ thống và bảo trì (FR-55 → FR-60)
3.3.2 Yêu cầu phi chức năng (Non-Functional Requirements – NFR)
Hệ thống có tổng cộng 33 yêu cầu phi chức năng, được phân thành các nhóm:
Hiệu suất (NFR-01 → NFR-04)
Độ tin cậy (NFR-05 → NFR-08)
Bảo mật (NFR-09 → NFR-13)
Tính tương thích (NFR-14 → NFR-16)
Khả năng sử dụng (NFR-17 → NFR-19)
Khả năng bảo trì (NFR-20 → NFR-23)
Tính khả chuyển (NFR-24 → NFR-25)
Khả năng mở rộng (NFR-26 → NFR-27)
Khả năng chịu lỗi & phục hồi (NFR-28 → NFR-30)
Tuân thủ (NFR-31 → NFR-33)
Kết quả đánh giá chất lượng các yêu cầu
Sau khi áp dụng bộ tiêu chí đánh giá chất lượng, các yêu cầu của hệ thống đạt được các kết quả sau:
100% yêu cầu là cần thiết (Necessary)
 → Không có yêu cầu dư thừa hoặc không phục vụ mục tiêu hệ thống.
100% yêu cầu rõ ràng và không mơ hồ (Unambiguous)
 → Mỗi yêu cầu chỉ có một cách hiểu duy nhất.
100% yêu cầu có thể kiểm chứng (Verifiable)
 → Có thể xây dựng test case để kiểm tra.
100% yêu cầu có khả năng truy vết (Traceable)
 → Liên kết được với nhóm chức năng và mục tiêu hệ thống.
100% yêu cầu khả thi (Feasible)
 → Phù hợp với công nghệ và phạm vi dự án.
100% yêu cầu đảm bảo tính đơn nhất (Singular)
 → Mỗi yêu cầu chỉ mô tả một chức năng; các yêu cầu phức hợp đã được phân rã thành các yêu cầu mức lá độc lập.
100% yêu cầu tuân thủ chuẩn SRS (Conforming)
 → Tuân thủ đúng định dạng, cách đánh số và cấu trúc tài liệu.
100% yêu cầu đạt tính đúng đắn (Correct)
 →Phản ánh chính xác nghiệp vụ quản lý học sinh và quy chế của Bộ GD&ĐT.
100% yêu cầu đạt tính đầy đủ (Complete)
 →  Bao phủ mọi kịch bản sử dụng, kể cả các trường hợp ngoại lệ 
100% yêu cầu không phụ thuộc cài đặt (Implementation-free)
 → Các yêu cầu chỉ tập trung vào việc "hệ thống cần làm gì" (What) chứ không đi sâu vào "làm như thế nào" (How).

CHƯƠNG 4. KIẾN TRÚC PHẦN MỀM
Vai trò của kiến trúc phần mềm với quá trình phát triển dự án phần mềm
 “Kiến trúc phần mềm là tập hợp các cấu trúc cần thiết để lập trình nên hệ thống phần mềm, bao gồm các phần tử, mối quan hệ giữa chúng và các thuộc tính của chúng”  (Morrow et al., n.d.)
 Kiến trúc phần mềm giúp việc quyết định thiết kế phần mềm dễ dàng hơn và cho phép tái sử dụng các thành phần và mẫu thiết kế của các dự án.
  Kiến trúc phần mềm đóng vai trò như bản thiết kế cho một hệ thống. Nó cung cấp một cái nhìn tổng thể để quản lý độ phức tạp của hệ thống, thiết lập một cơ chế giao tiếp và phối hợp của các thành phần
  Một số vai trò chính của kiến trúc phần mềm bao gồm:
Cung cấp thông tin cần thiết, định hướng cho các giai đoạn khác trong quá trình phát triển phần mềm: Dựa trên thiết kế đề ra, các bộ phận có thể tiến hành lập trình, xây dựng phần mềm theo đúng yêu cầu và hạn chế thấp nhất sai sót không mong muốn.
Là cơ sở để kiểm tra, phân tích tính chính xác, mức độ phù hợp, khả năng đáp ứng yêu cầu được đặt ra của một sản phẩm sau khi hoàn thành: Mỗi phần mềm ra đời đều có những sai số nhất định so với dự tính ban đầu. Lúc này, việc đối chiếu với thiết kế sẽ giúp tìm ra và khắc phục vấn đề nhanh chóng.
Đảm bảo rằng phần mềm được thiết kế và triển khai một cách khả dụng và bảo trì.
Cung cấp một cấu trúc tổng thể cho ứng dụng, giúp cho việc mở rộng và bảo trì phần mềm dễ dàng hơn.
Giảm thiểu chi phí phát triển bằng cách đảm bảo rằng các thành phần phần mềm có thể được tái sử dụng trong các dự án khác nhau. Cung cấp các giải pháp bảo mật để đảm bảo tính an toàn và bảo mật cho phần mềm. Kiến trúc phần mềm giúp các nhà phát triển phát hiện và khắc phục các lỗ hổng bảo mật và giảm thiểu rủi ro cho phần mềm
Một số kiến trúc phần mềm phổ biến
Kiến trúc đơn thể (Monolithic)
Đặc tả : Hệ thống chỉ gồm duy nhất 1 module, module này chứa mọi thứ của chương trình : danh sách các lệnh thực thi miêu tả giải thuật cần thực hiện	
Danh sách các biến dữ liệu bị xử lý
Giao tiếp giữa các thành phần là cục bộ và rất hiệu quả
Thích hợp cho những phần mềm nhỏ, đơn giản
Không thích hợp cho những phần mềm lớn và phức tạp

Kiến trúc MVC (Model-View-Controller)
-  Đặc tả : Hệ thống gồm 3 thành phần luận lý tương tác lẫn nhau :
+ Model quản lý dữ liệu và các tác vụ liên quan đến dữ liệu này.
+ View định nghĩa và quản lý cách thức dữ liệu được trình bày cho user.
+ Controller quản lý các tương tác với user như ấn phím, click chuột… và gửi thông tin tương tác này tới View và/hoặc Model.
-  Tình huống nên dùng : Hệ thống có nhiều cách để view và tương tác với dữ liệu, hoặc ta chưa biết trước các yêu cầu tương lai về sự tương tác và biểu diễn dữ liệu của chương trình.
-  Ưu điểm : cho phép dữ liệu thay đổi độc lập với cách thức thể hiện nó và ngược lại.
-  Khuyết điểm : có thể cần nhiều code hơn và code có thể phức tạp hơn khi mô hình dữ liệu và sự tương tác chỉ ở mức độ đơn giản.
Kiến trúc 3-layer
-          Đặc tả : Hệ thống gồm 3 thành phần:
●   	  Presentation Layer (GUI) : Lớp này có nhiệm vụ chính giao tiếp với người dùng. Nó gồm các thành phần giao diện ( winform, webform,…) và thực hiện các công việc như nhập liệu, hiển thị dữ liệu, kiểm tra tính đúng đắn dữ liệu trước khi gọi lớp Business Logic Layer (BLL).
●   	Business Logic Layer (BLL): Layer này phân ra 2 thành nhiệm vụ :
○   	 Đây là nơi đáp ứng các yêu cầu thao tác dữ liệu của GUI layer, xử lý chính nguồn dữ liệu từ Presentation Layer trước khi truyền xuống Data Access Layer và lưu xuống hệ quản trị CSDL.
○   	Đây còn là nơi kiểm tra các ràng buộc, tính toàn vẹn và hợp lệ dữ liệu, thực hiện tính toán và xử lý các yêu cầu nghiệp vụ, trước khi trả kết quả về Presentation Layer.
●   	Data Access Layer (DAL) : Lớp này có chức năng giao tiếp với hệ quản trị CSDL như thực hiện các công việc liên quan đến lưu trữ và truy vấn dữ liệu ( tìm kiếm, thêm, xóa, sửa,…).
●   	 Ưu điểm
○   	Việc phân chia thành từng lớp giúp cho code được tường minh hơn.
○   	Dễ bảo trì khi được phân chia, thì một thành phần của hệ thống sẽ dễ thay đổi.
○   	Dễ phát triển, tái sử dụng
○   	Dễ bàn giao
○    Dễ phân phối khối lượng công việc
Kiến trúc tổng thể của hệ thống
4.3.1 Tổng quan kiến trúc
Modular Monolithic Architecture
Trong đó:
Toàn bộ hệ thống được triển khai như một ứng dụng duy nhất (monolith)
Bên trong được chia thành các module độc lập theo chức năng

Hệ thống không tách thành nhiều service riêng biệt mà tổ chức logic nội bộ theo module để đảm bảo:
Dễ quản lý
Dễ phát triển
Dễ kiểm thử

4.3.2 Cấu trúc tổng thể hệ thống
Hệ thống bao gồm 3 phần chính:
a. Presentation Layer (Giao diện)
Nhận yêu cầu từ người dùng
Gửi request đến các module bên trong hệ thống

b. Application Core (Modular Monolith)
Đây là phần trung tâm của hệ thống, bao gồm các module:
Authentication Module
Student Module
Score Module
Conduct Module
Report Module

→ Tất cả module:
Chạy trong cùng một process
Giao tiếp qua internal function call (gọi hàm nội bộ)

c. Database
Dùng chung cho toàn bộ hệ thống
Lưu trữ dữ liệu học sinh, điểm, hạnh kiểm

4.3.3 Luồng xử lý trong kiến trúc
Người dùng gửi yêu cầu
Request đi vào hệ thống
Được điều hướng đến module tương ứng
Module xử lý nghiệp vụ
Truy vấn database nếu cần
Trả kết quả về giao diện

4.4.4 Đặc điểm của kiến trúc
Một ứng dụng duy nhất (single deployable unit)
Module hóa bên trong
Không có giao tiếp qua network giữa các module
Hiệu năng cao do không có overhead mạng


Lựa chọn mô hình kiến trúc cho hệ thống
4.4.1. Kiến trúc được lựa chọn
Dựa trên phân tích yêu cầu hệ thống trong tài liệu SRS, kiến trúc phù hợp cho dự án là:
Modular Monolithic Architecture (Kiến trúc nguyên khối dạng module)
Đây là kiến trúc trong đó toàn bộ hệ thống được triển khai như một ứng dụng duy nhất (monolithic), nhưng bên trong được chia thành các module độc lập theo chức năng.
4.4.2 Lý do lựa chọn kiến trúc 
	a. Đáp ứng yêu cầu hiệu suất (Performance)
Hệ thống cần:
Thời gian phản hồi ≤ 3 giây
Hỗ trợ ≥ 100 người dùng đồng thời
Phân tích:
Kiến trúc Modular Monolithic chạy trong một tiến trình duy nhất (single process)
Các module giao tiếp trực tiếp trong bộ nhớ (in-process call) → không có độ trễ mạng
Không phát sinh overhead như gọi API giữa các service
b. Đáp ứng yêu cầu bảo mật (Security)
	Hệ thống cần:
Xác thực và phân quyền
Mã hóa dữ liệu
Quản lý phiên, khóa tài khoản
Phân tích:
Kiến trúc tập trung giúp:
Dễ triển khai cơ chế xác thực (JWT) tại một điểm duy nhất
Kiểm soát phân quyền RBAC xuyên suốt hệ thống
Dữ liệu nằm trong một hệ thống duy nhất → giảm rủi ro rò rỉ giữa các service
Dễ áp dụng:
Hash mật khẩu
HTTPS
Logging truy vết
c. Đáp ứng yêu cầu khả năng tương thích (Compatibility)
Hệ thống cần:
Hệ thống chạy trên nhiều nền tảng: Web, Mobile, nhiều trình duyệt
Phân tích:
Kiến trúc monolithic + API:
Frontend (React/Web) giao tiếp qua REST API
Không phụ thuộc vào thiết bị client
Backend triển khai 1 hệ thống duy nhất:
Dễ deploy trên nhiều môi trường (Linux, Windows Server)
Không cần đồng bộ nhiều service → giảm lỗi tương thích
d. Đáp ứng yêu cầu khả năng sử dụng (Usability)
	Hệ thống cần:
Giao diện dễ dùng, phản hồi nhanh, hạn chế lỗi
Phân tích:

Do hiệu năng cao → giao diện phản hồi nhanh → cải thiện UX
Kiến trúc đơn giản:
Ít lỗi hệ thống phức tạp (distributed errors)
Dễ debug → giảm lỗi cho người dùng
Luồng xử lý tập trung:
Dễ kiểm soát validation
Thông báo lỗi rõ ràng
	4.4.3. Kết luận
→ Kiến trúc được lựa chọn giúp hệ thống đảm bảo tính dễ sử dụng thông qua giao diện phản hồi nhanh, rõ ràng và hạn chế lỗi trong quá trình thao tác. Đồng thời, với cấu trúc đơn giản và tập trung, hệ thống hoạt động ổn định, giảm thiểu các lỗi phát sinh trong quá trình sử dụng. Nhờ đó, người dùng có thể dễ dàng làm quen, thao tác thuận tiện và đạt hiệu quả cao khi sử dụng hệ thống.
Kiến trúc phần mềm của hệ thống
Tổng quan phân lớp
Backend của hệ thống được chia thành 4 lớp chính:
Controller Layer (Presentation/API Layer)
Service Layer (Business Logic Layer)
Repository Layer (Data Access Layer)
Model Layer (Domain Layer)
Luồng xử lý:
Client → Controller → Service → Repository → Database
Controller Layer
Vai trò: 
Là điểm tiếp nhận request từ client (frontend)
Xử lý HTTP request/response
Gọi Service để xử lý nghiệp vụ
Đặc điểm:
Không chứa logic nghiệp vụ phức tạp
Chỉ xử lý:
Mapping URL
Nhận dữ liệu đầu vào
Trả response
Nhiệm vụ chính
Validate dữ liệu cơ bản (format, null, type)
Kiểm tra authentication (JWT)
Trả HTTP status code (200, 400, 401, 500)
Service Layer
Vai trò
Là trung tâm của hệ thống
Chứa toàn bộ business logic
Chức năng
Xử lý nghiệp vụ:
Tính điểm trung bình
Xếp loại học lực
Kiểm tra rule hệ thống:
Không cho giáo viên chủ nhiệm 2 lớp
Không nhập điểm ngoài 
Ưu điểm
Tách biệt logic → dễ test (unit test)
Có thể tái sử dụng
Repository Layer (Data Access Layer)
Vai trò:
Giao tiếp trực tiếp với database
Chức năng:
CRUD dữ liệu:
Create
Read
Update
Delete
Đặc điểm:
Không chứa logic nghiệp vụ
Chỉ thao tác dữ liệu
Công nghệ:
ORM (Hibernate / Sequelize / JPA)
Query builder
Model Layer (Entity Layer)
Vai trò:
Đại diện cho các đối tượng dữ liệu trong hệ thống
Chức năng:
Mapping với bảng database
Định nghĩa thuộc tính
Luồng xử lý chi tiết (Use case: Nhập điểm)
Giáo viên nhập điểm trên giao diện
Frontend gửi request → /api/scores
Controller:
Nhận request
Validate dữ liệu
Service:
Kiểm tra quyền giáo viên
Kiểm tra điểm hợp lệ (0–10)
Tính toán nếu cần
Repository:
Lưu dữ liệu vào DB
Trả kết quả về client
Cơ sở dữ liệu mức vật lý của hệ thống
Mô hình hóa dữ liệu ban đầu
Xác định các thực thể và thuộc tính
		
Xác định các kiểu liên kết

STT
Kiểu thực thể
Kiểu liên kết/Bảng số
Kiểu thực thể
1
BackupRecords

RestoreRecords
2
User

BackupRecords
3
User

RestoreRecords
4
User

UserSession
5
User

Roles
6
User

Classes
7
User

PasswordResetRequests
8
User

Schedules
9
Schedules

Classes
10
Schedules

Subjects
11
Classes

Students
12
Classes

Subjects
13
Subjects

Students


Mô hình hóa thực thể liên kết mở rộng
-     	Giải mã ký hiệu

 
 
 
Kiểu thực thể và thuộc tính

 
 
Kiểu liên kết và bản số
Tên thuộc tính(*)
Kiểu thuộc tính đa trị






-     	Mô hình ERD mở rộng


Chuẩn hóa dữ liệu
Chuẩn hóa từ ERD mở rộng thành ERD kinh điển
-     	Xác định khóa cho các thực thể chính
STT
Tên thực thể
Khóa
Ghi chú
1
User
user_id


2
BackupRecords
backup_id


3
RestoreRecords
Restore_id


4
UserSession
session_id


5
Roles
role_id


6
PasswordResetRequests
passwordResetRequest_id


7
Schedules
schedule_id


8
Classes
class_id


9
Students
student_id


10
Subjects
subject_id




-     	Mô hình ERD kinh điển
+ Giải thích ký hiệu:

+ Mô hình ERD kinh điển

Chuyển từ ERD kinh điển thành ERD hạn chế

Xử lý LK 1-1

Xử lý LK n-n

Xác định các thuộc tính kết nối ( Khóa ngoài)
STT
Đầu 1
Đầu nhiều
Thuộc tính kết nối
Ghi chú





























































































Xác định các khóa chính và khóa ngoại của các kiểu thực thể

Kiểu thực thể
Khóa chính
Khóa ngoài








Mô hình ERD hạn chế
Ký hiệu
Mô hình ERD hạn chế

Chuyển mô hình ERD hạn chế thành mô hình quan hệ
Loại bỏ những thuộc tính không cần thiết
Các thực thể và thuộc tính mô hình quan hệ
Mô hình quan hệ
Ký hiệu
Mô hình quan hệ

Đặc tả cơ sở dữ liệu
Chuyển đổi từ tên thực thể sang bảng

Tên thực thể
Tên bảng















Đặc tả dữ liệu hệ thống


Users
Thuộc tính
Kiểu dữ liệu
Ràng buộc
Tên thuộc tính

















Cơ sở dữ liệu cuối cùng





  Một số thiết kế chi tiết theo kiến trúc được chọn
Đăng nhập
Input
Email hoặc tên đăng nhập (txtEmailOrUsername)
Mật khẩu (txtPassword — masked)
Output
Thành công: Thông báo "Đăng nhập thành công", chuyển hướng Dashboard theo vai trò (FR-07)
Thất bại:
"Không được để trống email/tên đăng nhập hoặc mật khẩu!"
"Sai tài khoản hoặc mật khẩu!"
"Bạn đã nhập sai 5 lần. Tài khoản tạm thời bị khóa trong 15 phút!"
"Tài khoản đã bị khóa. Vui lòng liên hệ quản trị viên!"

Process (Nghiệp vụ)
● Người dùng nhập email/tên đăng nhập và mật khẩu, ấn nút "Đăng nhập".
Nếu có trường bị bỏ trống → Thông báo "Không được để trống!" và dừng.
● Kiểm tra trạng thái khóa tạm thời.
Nếu tài khoản đang bị khóa tạm thời → Thông báo "Tài khoản tạm thời bị khóa. Vui lòng thử lại sau X phút!" và dừng.
● Tìm tài khoản theo email/tên đăng nhập.
Nếu không tìm thấy → Thông báo "Sai tài khoản hoặc mật khẩu!" và dừng.
● Kiểm tra tài khoản có bị khóa vĩnh viễn không.
Nếu bị khóa vĩnh viễn → Thông báo "Tài khoản đã bị khóa. Vui lòng liên hệ quản trị viên!" và dừng.
● So sánh mật khẩu với hash (bcrypt).
Nếu sai mật khẩu → Tăng failed_attempts lên 1. → Nếu failed_attempts >= 5: khóa tài khoản 15 phút, thông báo tương ứng và dừng. → Nếu chưa đủ 5 lần: thông báo "Sai tài khoản hoặc mật khẩu!" và dừng.
● Nếu tất cả hợp lệ → Reset failed_attempts về 0. → Tạo JWT token. → Ghi log đăng nhập → Điều hướng Dashboard theo vai trò 

Các module tham gia
Auth Module — Nhận request, điều phối toàn bộ luồng xác thực, điều hướng theo vai trò.
User Module — Quản lý dữ liệu người dùng. Expose IUserService.
Security Module — Mã hóa bcrypt, tạo JWT, RBAC. Expose ISecurityService.
AccountPolicy Module — Chính sách khóa tài khoản . Expose IAccountPolicyService.
Logging Module — Ghi log lịch sử đăng nhập . Expose ILoggingService.
 Sequence Diagram
![Sequence đăng nhập](./images/sequence_dangnhap.png)
Danh sách Unit

STT
Class
Method
Module
Input
Output
1
ViewLogin
btnDangNhap_click
Auth Module
txtEmailOrUsername,
txtPassword
Gọi AuthController.dangNhapRQ()
2
InputValidator
validate
Auth Module
emailOrUsername,
password
ValidationResult
3
AuthController
dangNhapRQ
Auth Module
emailOrUsername,
password
JSON success/redirect hoặc error
4
AuthService
authenticate
Auth Module
emailOrUsername,
password
User hoặc AuthException
5
AuthService
handleLoginSuccess
Auth Module
User
redirect URL theo vai trò
6
IAccountPolicyService
checkLockStatus
AccountPolicy Module
userId
LockStatus
7
IAccountPolicyService
incrementFailedAttempts
AccountPolicy Module
userId
int failedAttempts
8
IAccountPolicyService
lockAccount
AccountPolicy Module
userId,
durationMinutes
void
9
IAccountPolicyService
resetFailedAttempts
AccountPolicy Module
userId
void
10
IUserService
findByIdentifier
User Module
identifier
Optional<User>
11
ISecurityService
verifyPassword
Security Module
rawPassword,
hashedPassword
boolean
12
ISecurityService
generateJwt
Security Module
User
String JWT
13
ILoggingService
logLoginEvent
Logging Module
userId,success,
ip
void


Thiết kế từng Unit
🔹 btnDangNhap_click
Mục đích: Thu thập dữ liệu form, gọi Controller.
Module: Auth Module
Input: txtEmailOrUsername, txtPassword
Output: Gọi AuthController.dangNhapRQ()

🔹 validate
Mục đích: Kiểm tra dữ liệu đầu vào không rỗng trước khi xử lý.
Module: Auth Module
Input: emailOrUsername, password
Output: ValidationResult (valid, errorMessage)

🔹 dangNhapRQ
Mục đích: Nhận request, gọi validate rồi điều phối sang AuthService.
Module: Auth Module
Input: emailOrUsername, password
Output: JSON success + redirectURL hoặc error

🔹 authenticate
Mục đích: Điều phối toàn bộ luồng xác thực, gọi lần lượt các module qua interface.
Module: Auth Module
Input: emailOrUsername, password
Output: User entity hoặc AuthException

🔹 checkLockStatus
Mục đích: Kiểm tra tài khoản có đang bị khóa tạm thời không.
Module: AccountPolicy Module
Input: userId
Output: LockStatus (isLocked, remainingSeconds)

🔹 findByIdentifier
Mục đích: Tìm user theo email hoặc tên đăng nhập.
Module: User Module
Input: identifier
Output: Optional<User> hoặc null

🔹 verifyPassword
Mục đích: So sánh mật khẩu người dùng nhập với hash đã lưu (bcrypt).
Module: Security Module
Input: rawPassword, hashedPassword
Output: boolean

🔹 incrementFailedAttempts
Mục đích: Tăng số lần đăng nhập sai, khóa tài khoản nếu đủ 5 lần.
Module: AccountPolicy Module
Input: userId
Output: int failedAttempts

🔹 handleLoginSuccess
Mục đích: Tạo JWT, ghi log, trả URL điều hướng theo vai trò.
Module: Auth Module
Input: User
Output: redirect URL

🔹 lockAccount
Mục đích: Khóa tài khoản tạm thời 15 phút sau khi đăng nhập sai 5 lần (NFR-13).
Module: AccountPolicy Module
Input: userId, durationMinutes
Output: void
🔹 resetFailedAttempts
Mục đích: Reset số lần đăng nhập sai về 0 sau khi đăng nhập thành công.
Module: AccountPolicy Module
Input: userId
Output: void
🔹 generateJwt
Mục đích: Tạo JWT token chứa thông tin user và role để xác thực các request tiếp theo.
Module: Security Module
Input: User
Output: String JWT token
🔹 logLoginEvent
Mục đích: Ghi log lịch sử đăng nhập thành công hoặc thất bại vào DB (FR-55).
Module: Logging Module
Input: userId, success, ipAddress
Output: void



Mã
Nội dung
MS_01
"Không được để trống email/tên đăng nhập hoặc mật khẩu!"
MS_02
"Tài khoản tạm thời bị khóa. Vui lòng thử lại sau X phút!"
MS_03
"Sai tài khoản hoặc mật khẩu!"
MS_04
"Bạn đã nhập sai 5 lần. Tài khoản tạm thời bị khóa trong 15 phút!"
MS_05
"Đăng nhập thành công"



Thêm hồ sơ học sinh 
Input
Họ tên học sinh (txtHoTen) — bắt buộc
Ngày sinh (dtpNgaySinh) — bắt buộc
Giới tính (cboGioiTinh) — bắt buộc
Địa chỉ (txtDiaChi) — bắt buộc
Dân tộc (txtDanToc) — bắt buộc
Tôn giáo (txtTonGiao) — bắt buộc
Họ tên cha (txtHoTenCha) — bắt buộc
Họ tên mẹ (txtHoTenMe) — bắt buộc
Số điện thoại liên hệ (txtSDT) — bắt buộc
Output
Thành công: Thông báo "Thêm hồ sơ học sinh thành công!", hệ thống sinh mã học sinh tự động và lưu vào DB
Thất bại:
"Vui lòng nhập đầy đủ thông tin bắt buộc!" (MS_01)
"Họ tên không hợp lệ!" (MS_02)
"Ngày sinh không hợp lệ!" (MS_03)
"Số điện thoại không hợp lệ!" (MS_04)
"Thêm hồ sơ học sinh thất bại, vui lòng thử lại!" (MS_05)

Process
● Nhân viên văn phòng nhập đầy đủ thông tin và ấn "Lưu".
Nếu có trường bắt buộc bị bỏ trống → Thông báo MS_01 và dừng.
● Kiểm tra định dạng từng trường:
Nếu họ tên học sinh/cha/mẹ chứa ký tự đặc biệt hoặc số → Thông báo MS_02 và dừng.
Nếu ngày sinh không hợp lệ (tương lai, hoặc > 20 tuổi, < 10 tuổi) → Thông báo MS_03 và dừng.
Nếu số điện thoại sai định dạng (không phải 10 số, không bắt đầu bằng 0) → Thông báo MS_04 và dừng.
● Nếu tất cả hợp lệ → Sinh mã học sinh tự động (format: HS + năm hiện tại + số thứ tự tăng dần, ví dụ: HS2024001). → Lưu hồ sơ vào DB. → Ghi log thao tác (FR-55). → Thông báo "Thêm hồ sơ học sinh thành công!".
Module tham gia
Student Module — Nhận request, điều phối luồng thêm hồ sơ, sinh mã học sinh. Expose IStudentService.
Validation Module — Kiểm tra định dạng và tính hợp lệ của dữ liệu đầu vào. Expose IValidationService.
Logging Module — Ghi log thao tác thêm hồ sơ (FR-55). Expose ILoggingService.

Sequence Diagram


 Danh sách Unit
STT
Class
Method
Module
Input
Output
1
ViewAddStudent
btnLuu_click
Student Module
Các field form
Gọi StudentController.themHoSoRQ()
2
StudentController
themHoSoRQ
Student Module
StudentDTO
JSON success hoặc error
3
IValidationService
validateRequired
Validation Module
StudentDTO
ValidationResult
4
IValidationService
validateFormat
Validation Module
StudentDTO
ValidationResult
5
IStudentService
generateMaHocSinh
Student Module
—
String mã học sinh
6
IStudentService
saveHoSo
Student Module
StudentDTO,maHocSinh
boolean
7
ILoggingService
logAction
Logging Module
userId,Action,
entityId
void

Thiết kế từng Unit
🔹 btnLuu_click
Mục đích: Thu thập dữ liệu từ form, gọi Controller.
Module: Student Module
Input: Tất cả field trên form
Output: Gọi StudentController.themHoSoRQ(studentDTO)

🔹 themHoSoRQ
Mục đích: Nhận request, gọi validation rồi điều phối sang Service, trả JSON về View.
Module: Student Module
Input: StudentDTO
Output: JSON success hoặc error

🔹 validateRequired
Mục đích: Kiểm tra tất cả trường bắt buộc không được bỏ trống.
Module: Validation Module
Input: StudentDTO
                 Output: ValidationResult
🔹 validateFormat
Mục đích: Kiểm tra định dạng họ tên, ngày sinh, số điện thoại.
Module: Validation Module
Input: StudentDTO
Output: ValidationResult

🔹 generateMaHocSinh
Mục đích: Sinh mã học sinh tự động theo format HS + năm + số thứ tự.
Module: Student Module
Input: —
                 Output: String mã học sinh (ví dụ: HS2024001)
🔹 saveHoSo
Mục đích: Lưu hồ sơ học sinh vào DB.
Module: Student Module
Input: StudentDTO, maHocSinh
Output: boolean

🔹 logAction
Mục đích: Ghi log thao tác thêm hồ sơ (FR-55).
Module: Logging Module
Input: userId, action, entityId
Output: void


Mã
Nội dung
MS_01
"Vui lòng nhập đầy đủ thông tin bắt buộc!"
MS_02
"Họ tên không hợp lệ!"
MS_03
"Ngày sinh không hợp lệ!"
MS_04
"Số điện thoại không hợp lệ!"
MS_05
"Thêm hồ sơ học sinh thất bại, vui lòng thử lại!"
MS_06
"Thêm hồ sơ học sinh thành công!"


Tìm kiếm học sinh
Input
Mã học sinh (txtMaHS) — không bắt buộc
Họ tên (txtHoTen) — không bắt buộc
Lớp (cboLop) — không bắt buộc
Ít nhất 1 trong 3 tiêu chí phải được nhập.
Output
Thành công: Hiển thị danh sách học sinh khớp với tiêu chí tìm kiếm
Thất bại:
"Vui lòng nhập ít nhất một tiêu chí tìm kiếm!" (MS_01)
"Không tìm thấy học sinh phù hợp!" (MS_02)
"Tiêu chí tìm kiếm không hợp lệ!" (MS_03)


Process 
● Người dùng nhập tiêu chí tìm kiếm và ấn "Tìm kiếm".
Nếu tất cả 3 trường đều bỏ trống → Thông báo MS_01 và dừng.
● Kiểm tra định dạng tiêu chí:
Nếu txtMaHS được nhập nhưng sai định dạng (không bắt đầu bằng HS) → Thông báo MS_03 và dừng.
Nếu txtHoTen được nhập nhưng chứa ký tự đặc biệt hoặc số → Thông báo MS_03 và dừng.
● Truy vấn DB theo tiêu chí đã nhập (có thể kết hợp nhiều tiêu chí cùng lúc).
Nếu không có kết quả → Thông báo MS_02.
Nếu có kết quả → Hiển thị danh sách học sinh (mã HS, họ tên, ngày sinh, giới tính, lớp).
Module tham gia
Student Module — Nhận request tìm kiếm, điều phối truy vấn. Expose IStudentService.
Validation Module — Kiểm tra tiêu chí đầu vào. Expose IValidationService.
Logging Module — Ghi log thao tác tìm kiếm (FR-55). Expose ILoggingService.





Sequence Diagram

Danh sách Unit
STT
Class
Method
Module
Input
Output
1
ViewSearchStudent
btnTimKiem_click
Student Module
txtMaHS,
txtHoTen,
cboLop
Gọi StudentController.timKiemRQ()
2
StudentController
timKiemRQ
Student Module
SearchDTO
JSON danh sách hoặc error
3
IValidationService
validateSearchInput
Validation Module
SearchDTO
ValidationResult
4
IValidationService
validateSearchFormat
Validation Module
SearchDTO
ValidationResult
5
IStudentService
searchHocSinh
Student Module
SearchDTO
List<StudentDTO>
6
ILoggingService
logAction
Logging Module
userId,
Action,
keyword
void

Thiết kế từng Unit
🔹 btnTimKiem_click
Mục đích: Thu thập tiêu chí tìm kiếm từ form, gọi Controller.
Module: Student Module
Input: txtMaHS, txtHoTen, cboLop
Output: Gọi StudentController.timKiemRQ(searchDTO)
🔹 timKiemRQ
Mục đích: Nhận request, gọi validation rồi điều phối sang Service, trả JSON về View.
Module: Student Module
Input: SearchDTO
Output: JSON danh sách học sinh hoặc error

🔹 validateSearchInput
Mục đích: Kiểm tra ít nhất một tiêu chí được nhập.
Module: Validation Module
Input: SearchDTO
Output: ValidationResult
🔹 validateSearchFormat
Mục đích: Kiểm tra định dạng tiêu chí đã nhập.
Module: Validation Module
Input: SearchDTO
Output: ValidationResult
🔹 searchHocSinh
Mục đích: Truy vấn DB theo tiêu chí, trả danh sách kết quả.
Module: Student Module
Input: SearchDTO
Output: List<StudentDTO>
🔹 logAction
Mục đích: Ghi log thao tác tìm kiếm (FR-55).
Module: Logging Module
Input: userId, action, keyword
Output: void

Mã
Nội dung
MS_01
"Vui lòng nhập ít nhất một tiêu chí tìm kiếm!"
MS_02
"Không tìm thấy học sinh phù hợp!"
MS_03
"Tiêu chí tìm kiếm không hợp lệ!"


Tạo lớp học
Input
Tên lớp (txtTenLop) — bắt buộc
Khối (cboKhoi) — bắt buộc
Năm học (txtNamHoc) — bắt buộc
Tất cả 3 trường đều bắt buộc nhập.
Output
Thành công: Lớp học mới được tạo, hiển thị thông báo "Tạo lớp học thành công!" (MS_04)
Thất bại:
"Vui lòng nhập đầy đủ thông tin lớp học!" (MS_01)
"Thông tin lớp học không hợp lệ!" (MS_02)
"Lớp học đã tồn tại trong hệ thống!" (MS_03)
Process
Nhân viên văn phòng nhập thông tin lớp học và ấn "Tạo lớp".
Nếu bất kỳ trường nào bị bỏ trống → Thông báo MS_01 và dừng.
Kiểm tra định dạng:
Nếu txtTenLop chứa ký tự đặc biệt hoặc không khớp định dạng (vd: 10A1, 11B2) → Thông báo MS_02 và dừng.
Nếu cboKhoi không thuộc [10, 11, 12] → Thông báo MS_02 và dừng.
Nếu txtNamHoc không đúng định dạng YYYY-YYYY hoặc năm sau ≠ năm trước + 1 → Thông báo MS_02 và dừng.
Kiểm tra trùng: Nếu đã tồn tại lớp có cùng tên lớp + khối + năm học trong DB → Thông báo MS_03 và dừng.
Lưu lớp mới vào DB.
Ghi log thao tác tạo lớp (FR-55).
Trả về MS_04 và hiển thị thông tin lớp vừa tạo.
Module tham gia
Class Module — Nhận request tạo lớp, điều phối nghiệp vụ. Expose IClassService.
Validation Module — Kiểm tra đầu vào và định dạng. Expose IValidationService.
Logging Module — Ghi log thao tác tạo lớp (FR-55). Expose ILoggingService.






Sequence Diagram

 Danh sách Unit 
STT
Class
Method
Module
Input
Output
1
ViewCreateClass
btnTaoLop_click
Class Module
txtTenLop, cboKhoi, txtNamHoc
Gọi ClassController.taoLopRQ()
2
ClassController
taoLopRQ
Class Module
ClassDTO
JSON thành công hoặc error
3
IValidationService
validateCreateInput
Validation Module
ClassDTO
ValidationResult
4
IValidationService
validateCreateFormat
Validation Module
ClassDTO
ValidationResult
5
IClassService
checkDuplicate
Class Module
ClassDTO
boolean
6
IClassService
createLopHoc
Class Module
ClassDTO
ClassDTO (saved)
7
ILoggingService
logAction
Logging Module
userId, action, lopId
void

Thiết kế từng Unit
🔹 btnTaoLop_click
Mục đích: Thu thập thông tin lớp từ form, gọi Controller.
Module: Class Module  |  Input: txtTenLop, cboKhoi, txtNamHoc  |  Output: Gọi ClassController.taoLopRQ(classDTO)








🔹 taoLopRQ
Mục đích: Nhận request, gọi validation, kiểm tra trùng, lưu DB, trả kết quả về View.
                               Module: Class Module  |  Input: ClassDTO  |  Output: JSON thành công hoặc error




🔹 validateCreateInputMục đích: Kiểm tra tất cả trường bắt buộc đều được nhập
      Module: Validation Module  |  Input: ClassDTO  |  Output: ValidationResult
🔹 validateCreateFormat
Mục đích: Kiểm tra định dạng tên lớp, khối, năm học.
Module: Validation Module  |  Input: ClassDTO  |  Output: ValidationResult



🔹 checkDuplicate
Mục đích: Kiểm tra lớp có cùng tên + khối + năm học đã tồn tại chưa.
Module: Class Module  |  Input: ClassDTO  |  Output: boolean
🔹 createLopHoc
Mục đích: Lưu lớp học mới vào DB, trả về bản ghi đã tạo.
Module: Class Module  |  Input: ClassDTO  |  Output: ClassDTO (saved)


🔹 logAction
Mục đích: Ghi log thao tác tạo lớp (FR-55).
Module: Logging Module  |  Input: userId, action, lopId  |  Output: void


Mã
Nội dung
MS_01
"Vui lòng nhập đầy đủ thông tin lớp học!"
MS_02
"Thông tin lớp học không hợp lệ!"
MS_03
"Lớp học đã tồn tại trong hệ thống!"
MS_04
"Tạo lớp học thành công!"



Nhập điểm học sinh
Input
Mã học sinh (txtMaHS) — bắt buộc
Môn học (cboMonHoc) — bắt buộc
Học kỳ (cboHocKy) — bắt buộc
Điểm miệng (txtDiemMieng) — tuỳ chọn (0–10, tối đa 1 chữ số thập phân)
Điểm 15 phút (txtDiem15P) — tuỳ chọn (0–10, tối đa 1 chữ số thập phân)
Điểm 1 tiết (txtDiem1Tiet) — tuỳ chọn (0–10, tối đa 1 chữ số thập phân)
Điểm học kỳ (txtDiemHK) — tuỳ chọn (0–10, tối đa 1 chữ số thập phân)

Output
Thành công:
Điểm được lưu vào hệ thống, hiển thị thông báo "Nhập điểm thành công!" (MS_05)
Process
Giáo viên chọn môn học, học kỳ, nhập mã học sinh và các loại điểm, sau đó ấn "Lưu điểm".
Nếu mã học sinh, môn học hoặc học kỳ bị bỏ trống → Thông báo MS_01 và dừng.
Kiểm tra quyền: Nếu giáo viên không được phân công dạy môn học đã chọn (FR-46) → Thông báo MS_04 và dừng.
Kiểm tra tồn tại: Nếu mã học sinh không có trong DB → Thông báo MS_03 và dừng.
Kiểm tra định dạng điểm:
Nếu bất kỳ điểm nào được nhập mà không thuộc [0.0 – 10.0] hoặc sai định dạng số → Thông báo MS_02 và dừng.
Nếu không có loại điểm nào được nhập → Thông báo MS_01 và dừng.
Lưu điểm vào DB (insert hoặc update nếu đã tồn tại bản ghi).
Ghi log thao tác nhập điểm (FR-55).
Trả về MS_05 và hiển thị lại điểm vừa lưu.
Module tham gia
Score Module — Nhận request nhập điểm, điều phối nghiệp vụ. Expose IScoreService.
Validation Module — Kiểm tra đầu vào, định dạng điểm, quyền giáo viên. Expose IValidationService.
Student Module — Kiểm tra sự tồn tại của học sinh. Expose IStudentService.
Logging Module — Ghi log thao tác nhập điểm (FR-55). Expose ILoggingService.




Sequence Diagram

Danh sách Unit

STT
Class
Method
Module
Input
Output
1
ViewEnterScore
btnLuuDiem_click
Score Module
txtMaHS, cboMonHoc, cboHocKy, txtDiemMieng, txtDiem15P, txtDiem1Tiet, txtDiemHK
Gọi ScoreController.nhapDiemRQ()
2
ScoreController
nhapDiemRQ
Score Module
ScoreDTO
JSON thành công hoặc error
3
IValidationService
validateScoreInput
Validation Module
ScoreDTO
ValidationResult
4
IValidationService
validateScoreFormat
Validation Module
ScoreDTO
ValidationResult
5
IValidationService
validateTeacherPermission
Validation Module
teacherId, monHocId
boolean
6
IStudentService
checkStudentExists
Student Module
maHS
boolean
7
IScoreService
saveScore
Score Module
ScoreDTO
ScoreDTO (saved)
8
ILoggingService
logAction
Logging Module
userId, action, scoreId
void

Thiết kế từng Unit
🔹 btnLuuDiem_click
Mục đích: Thu thập thông tin điểm từ form, gọi Controller. Module: Score Module | Input: txtMaHS, cboMonHoc, cboHocKy, txtDiemMieng, txtDiem15P, txtDiem1Tiet, txtDiemHK | Output: Gọi ScoreController.nhapDiemRQ(scoreDTO)








🔹 nhapDiemRQ
Mục đích: Nhận request, gọi validation, kiểm tra quyền, kiểm tra HS, lưu DB, trả kết quả về View.
Module: Score Module | Input: ScoreDTO | Output: JSON thành công hoặc error

🔹 validateScoreInput
Mục đích: Kiểm tra các trường bắt buộc không bị bỏ trống và ít nhất một loại điểm được nhập. Module: Validation Module | Input: ScoreDTO | Output: ValidationResult (valid = true / MS_01)

🔹 validateScoreFormat
Mục đích: Kiểm tra từng giá trị điểm được nhập có nằm trong khoảng [0.0 – 10.0] và đúng định dạng số.
Module: Validation Module | Input: ScoreDTO | Output: ValidationResult (valid = true / MS_02)


🔹 validateTeacherPermission
Mục đích: Kiểm tra giáo viên có được phân công giảng dạy môn học đã chọn hay không (liên kết FR-46). Module: Validation Module | Input: teacherId, monHocId | Output: boolean (true = có quyền / false = không có quyền)


🔹 checkStudentExists
Mục đích: Kiểm tra mã học sinh có tồn tại trong hệ thống hay không. Module: Student Module | Input: maHS (String) | Output: boolean (true = tồn tại / false = không tồn tại)

🔹 saveScore
Mục đích: Lưu điểm học sinh vào DB, thực hiện INSERT nếu chưa có bản ghi, UPDATE nếu đã tồn tại. Module: Score Module | Input: ScoreDTO | Output: ScoreDTO (saved)

🔹 logAction
Mục đích: Ghi lại lịch sử thao tác nhập điểm vào hệ thống log (FR-55). Module: Logging Module | Input: userId, action, scoreId | Output: void




Mã
Nội dung
MS_01
"Vui lòng nhập đầy đủ thông tin bắt buộc!"
MS_02
"Dữ liệu điểm không hợp lệ!"
MS_03
"Học sinh không tồn tại trong hệ thống!"
MS_04
"Giáo viên không có quyền nhập điểm môn này!"
MS_05
"Nhập điểm thành công!"
MS_06
"Lưu điểm thất bại, vui lòng thử lại!"


Tạo thời khoá biểu
Input
Lớp (cboLop) — bắt buộc
Học kỳ (cboHocKy) — bắt buộc
Môn học (cboMonHoc) — bắt buộc
Giáo viên phụ trách (cboGiaoVien) — bắt buộc
Thứ (cboThu) — bắt buộc (Thứ 2 – Thứ 7)
Tiết (cboTiet) — bắt buộc (Tiết 1 – Tiết 10)
Phòng học (txtPhongHoc) — bắt buộc
Output
Thành công: "Tạo thời khóa biểu thành công!" (MS_06)
Thất bại:
"Vui lòng nhập đầy đủ thông tin bắt buộc!" (MS_01)
"Giáo viên đã có lịch dạy vào tiết này!" (MS_02)
"Lớp đã có môn học vào tiết này!" (MS_03)
"Phòng học đã được sử dụng vào tiết này!" (MS_04)
"Giáo viên không được phân công dạy môn học này!" (MS_05)
"Tạo thời khóa biểu thất bại, vui lòng thử lại!" (MS_07)
Process
● Nhân viên văn phòng chọn lớp, học kỳ, môn học, giáo viên, thứ, tiết, phòng học rồi ấn "Lưu".
Nếu có trường bắt buộc bị bỏ trống → Thông báo MS_01 và dừng.
● Kiểm tra phân công giáo viên:
Nếu giáo viên không được phân công dạy môn học đã chọn → Thông báo MS_05 và dừng.
● Kiểm tra trùng lịch giáo viên:
Nếu giáo viên đã có tiết dạy vào đúng thứ + tiết đó (bất kể lớp nào) → Thông báo MS_02 và dừng.
● Kiểm tra trùng lịch lớp:
Nếu lớp đã có môn học vào đúng thứ + tiết đó → Thông báo MS_03 và dừng.
● Kiểm tra trùng phòng học:
Nếu phòng học đã được sử dụng vào đúng thứ + tiết đó → Thông báo MS_04 và dừng.
● Nếu tất cả hợp lệ → Lưu thời khóa biểu vào DB. → Ghi log thao tác (FR-55). → Thông báo MS_06.
Module tham gia
Schedule Module — Nhận request, điều phối luồng tạo thời khóa biểu. Expose IScheduleService.
Validation Module — Kiểm tra dữ liệu đầu vào, phân công giáo viên, trùng lịch. Expose IValidationService.
Logging Module — Ghi log thao tác (FR-55). Expose ILoggingService.






Sequence Diagram

Danh sách Unit 

STT
Class
Method
Module
Input
Output
1
ViewCreateSchedule
btnLuu_click
Schedule Module
Các field form
Gọi ScheduleController.taoTKBRQ()
2
ScheduleController
taoTKBRQ
Schedule Module
ScheduleDTO
JSON success hoặc error
3
IValidationService
validateRequired
Validation Module
ScheduleDTO
ValidationResult
4
IValidationService
validateTeacherAssignment
Validation Module
teacherId,
monHocId
boolean
5
IValidationService
validateTeacherConflict
Validation Module
teacherId,
thu,tiet,hocKy
boolean
6
IValidationService
validateClassConflict
Validation Module
lopId, thu, tiet, hocKy
boolean
7
IValidationService
validateRoomConflict
Validation Module
phongHoc, thu, tiet, hocKy
boolean
8
IScheduleService
saveTKB
Schedule Module
ScheduleDTO
boolean
9
ILoggingService
logAction
Logging Module
userId, action, scheduleId
void


Thiết kế từng Unit
🔹 btnLuu_click
Mục đích: Thu thập dữ liệu form, gọi Controller.
Module: Schedule Module
Input: cboLop, cboHocKy, cboMonHoc, cboGiaoVien, cboThu, cboTiet, txtPhongHoc
Output: Gọi ScheduleController.taoTKBRQ(scheduleDTO)

🔹 taoTKBRQ
Mục đích: Nhận request, gọi lần lượt các bước validation rồi lưu, trả JSON về View.
Module: Schedule Module
Input: ScheduleDTO
Output: JSON success hoặc error

🔹 validateRequired
Mục đích: Kiểm tra tất cả trường bắt buộc không được bỏ trống.
Module: Validation Module
Input: ScheduleDTO
Output: ValidationResult

🔹 validateTeacherAssignment
Mục đích: Kiểm tra giáo viên có được phân công dạy môn học đã chọn không.
Module: Validation Module
Input: teacherId, monHocId
Output: boolean

🔹 validateTeacherConflict
Mục đích: Kiểm tra giáo viên có bị trùng lịch dạy vào thứ + tiết đó không.
Module: Validation Module
Input: teacherId, thu, tiet, hocKy
Output: boolean

🔹 validateClassConflict
Mục đích: Kiểm tra lớp có bị trùng lịch vào thứ + tiết đó không.
Module: Validation Module
Input: lopId, thu, tiet, hocKy
Output: boolean

🔹 validateRoomConflict
Mục đích: Kiểm tra phòng học có bị trùng lịch vào thứ + tiết đó không.
Module: Validation Module
Input: phongHoc, thu, tiet, hocKy
Output: boolean

🔹 saveTKB
Mục đích: Lưu thời khóa biểu vào DB.
Module: Schedule Module
Input: ScheduleDTO
Output: boolean

🔹 logAction
Mục đích: Ghi log thao tác tạo thời khóa biểu (FR-55).
Module: Logging Module
Input: userId, action, scheduleId
Output: void



Mã
Nội dung
MS_01
"Vui lòng nhập đầy đủ thông tin bắt buộc!"
MS_02
"Giáo viên đã có lịch dạy vào tiết này!"
MS_03
"Lớp đã có môn học vào tiết này!"
MS_04
"Phòng học đã được sử dụng vào tiết này!"
MS_05
"Giáo viên không được phân công dạy môn học này!"
MS_06
"Tạo thời khóa biểu thành công!"
MS_07
"Tạo thời khóa biểu thất bại, vui lòng thử lại!"


Phân công giảng dạy
Input
Giáo viên (cboGiaoVien) — bắt buộc
Môn học (cboMonHoc) — bắt buộc
Lớp (cboLop) — bắt buộc
Học kỳ (cboHocKy) — bắt buộc
Output
Thành công: "Phân công giảng dạy thành công!" (MS_04)
Thất bại:
"Vui lòng nhập đầy đủ thông tin bắt buộc!" (MS_01)
"Giáo viên không đủ điều kiện dạy môn học này!" (MS_02)
"Phân công này đã tồn tại trong hệ thống!" (MS_03)
"Phân công giảng dạy thất bại, vui lòng thử lại!" (MS_05)
Process
● Nhân viên văn phòng chọn giáo viên, môn học, lớp, học kỳ rồi ấn "Lưu".
Nếu có trường bắt buộc bị bỏ trống → Thông báo MS_01 và dừng.
● Kiểm tra điều kiện giáo viên:
Nếu giáo viên không có chuyên môn phù hợp với môn học đã chọn → Thông báo MS_02 và dừng.
● Kiểm tra trùng phân công:
Nếu đã tồn tại bản ghi phân công với cùng giáo viên + môn học + lớp + học kỳ → Thông báo MS_03 và dừng.
● Nếu tất cả hợp lệ → Lưu phân công vào DB. → Ghi log thao tác (FR-55). → Thông báo MS_04.
Module tham gia
Assignment Module — Nhận request, điều phối luồng phân công giảng dạy. Expose IAssignmentService.
Validation Module — Kiểm tra dữ liệu đầu vào, điều kiện giáo viên, trùng phân công. Expose IValidationService.
Logging Module — Ghi log thao tác phân công (FR-55). Expose ILoggingService.
Sequence Diagram



Danh sách Unit
STT
Class
Method
Module
Input
Output
1
ViewAssignTeacher
btnLuu_click
Assignment Module
cboGiaoVien, cboMonHoc, cboLop, cboHocKy
Gọi AssignmentController.phanCongRQ()
2
AssignmentController
phanCongRQ
Assignment Module
AssignmentDTO
JSON success hoặc error
3
IValidationService
validateRequired
Validation Module
AssignmentDTO
ValidationResult
4
IValidationService
validateTeacherQualification
Validation Module
teacherId, monHocId
boolean
5
IValidationService
validateDuplicateAssignment
Validation Module
AssignmentDTO
boolean
6
IAssignmentService
saveAssignment
Assignment Module
AssignmentDTO
boolean
7
ILoggingService
logAction
Logging Module
userId, action, assignmentId
void

Thiết kế các Unit
🔹 btnLuu_click
Mục đích: Thu thập dữ liệu form, gọi Controller.
Module: Assignment Module
Input: cboGiaoVien, cboMonHoc, cboLop, cboHocKy
Output: Gọi AssignmentController.phanCongRQ(assignmentDTO)

🔹 phanCongRQ
Mục đích: Nhận request, gọi lần lượt các bước validation rồi lưu, trả JSON về View.
Module: Assignment Module
Input: AssignmentDTO
Output: JSON success hoặc error


🔹 validateRequired
Mục đích: Kiểm tra tất cả trường bắt buộc không được bỏ trống.
Module: Validation Module
Input: AssignmentDTO
Output: ValidationResult

🔹 validateTeacherQualification
Mục đích: Kiểm tra giáo viên có chuyên môn phù hợp với môn học không.
Module: Validation Module
Input: teacherId, monHocId
Output: boolean

🔹 validateDuplicateAssignment
Mục đích: Kiểm tra phân công đã tồn tại chưa (cùng giáo viên + môn + lớp + học kỳ).
Module: Validation Module
Input: AssignmentDTO
Output: boolean

🔹 saveAssignment
Mục đích: Lưu phân công giảng dạy vào DB.
Module: Assignment Module
Input: AssignmentDTO
Output: boolean


🔹 logAction
Mục đích: Ghi log thao tác phân công giảng dạy (FR-55).
Module: Logging Module
Input: userId, action, assignmentId
Output: void


Mã
Nội dung
MS_01
"Vui lòng nhập đầy đủ thông tin bắt buộc!"
MS_02
"Giáo viên không đủ điều kiện dạy môn học này!"
MS_03
"Phân công này đã tồn tại trong hệ thống!"
MS_04
"Phân công giảng dạy thành công!"
MS_05
"Phân công giảng dạy thất bại, vui lòng thử lại!"



Yêu cầu đặt lại mật khẩu
Input
Địa chỉ email (txtEmail) — bắt buộc
Output
Thành công: "Hệ thống đã gửi email hướng dẫn đặt lại mật khẩu. Vui lòng kiểm tra hộp thư!" (MS_04)
Thất bại:
"Vui lòng nhập địa chỉ email!" (MS_01)
"Định dạng email không hợp lệ!" (MS_02)
"Email không tồn tại trong hệ thống!" (MS_03)
"Gửi email thất bại, vui lòng thử lại!" (MS_05)
Process
● Người dùng nhập email và ấn "Gửi yêu cầu".
Nếu trường email bị bỏ trống → Thông báo MS_01 và dừng.
● Kiểm tra định dạng email:
Nếu email sai định dạng (không có @, không có domain) → Thông báo MS_02 và dừng.
● Kiểm tra email tồn tại trong hệ thống:
Nếu email không có trong DB → Thông báo MS_03 và dừng.
● Nếu email hợp lệ và tồn tại → Tạo token đặt lại mật khẩu (random, unique, hết hạn sau 15 phút). → Lưu token vào DB kèm thời gian hết hạn. → Tạo link đặt lại mật khẩu: https://domain/reset-password?token=xxx. → Gửi email chứa link đến địa chỉ email người dùng.
Nếu gửi email thất bại → Thông báo MS_05 và dừng.
● Ghi log thao tác (FR-55). → Thông báo MS_04.
Module tham gia
Auth Module — Nhận request, điều phối luồng yêu cầu đặt lại mật khẩu. Expose IAuthService.
Validation Module — Kiểm tra input, định dạng email. Expose IValidationService.
User Module — Kiểm tra email tồn tại trong hệ thống. Expose IUserService.
Security Module — Tạo reset token. Expose ISecurityService.
Email Module — Tạo nội dung và gửi email. Expose IEmailService.
Logging Module — Ghi log thao tác (FR-55). Expose ILoggingService.






Sequence Diagram

Danh sách Unit

STT
Class
Method
Module
Input
Output
1
ViewForgotPassword
btnGuiYeuCau_click
Auth Module
txtEmail
Gọi AuthController.yeuCauResetRQ()
2
AuthController
yeuCauResetRQ
Auth Module
email
JSON success hoặc error
3
IValidationService
validateRequired
Validation Module
email
ValidationResult
4
IValidationService
validateEmailFormat
Validation Module
email
ValidationResult
5
IUserService
checkEmailExists
User Module
email
boolean
6
ISecurityService
generateResetToken
Security Module
userId
String token
7
IAuthService
saveResetToken
Auth Module
userId, token, expiredAt
boolean
8
IEmailService
sendResetEmail
Email Module
email, resetLink
boolean
9
ILoggingService
logAction
Logging Module
userId, action
void


Thiết kế từng Unit
🔹 btnGuiYeuCau_click
Mục đích: Thu thập email từ form, gọi Controller.
Module: Auth Module
Input: txtEmail
Output: Gọi AuthController.yeuCauResetRQ(email)

🔹 yeuCauResetRQ
Mục đích: Nhận request, gọi lần lượt validation, tạo token, gửi email, trả JSON về View.
Module: Auth Module
Input: email
Output: JSON success hoặc error

🔹 validateRequired
Mục đích: Kiểm tra trường email không được bỏ trống.
Module: Validation Module
Input: email
Output: ValidationResult
 
🔹 validateEmailFormat
Mục đích: Kiểm tra định dạng email hợp lệ.
Module: Validation Module
Input: email
Output: ValidationResult

🔹 checkEmailExists
Mục đích: Kiểm tra email có tồn tại trong hệ thống không.
Module: User Module
Input: email
Output: boolean

🔹 generateResetToken
Mục đích: Tạo token ngẫu nhiên, duy nhất, hết hạn sau 15 phút.
Module: Security Module
Input: userId
Output: String token

🔹 saveResetToken
Mục đích: Lưu token và thời gian hết hạn vào DB.
Module: Auth Module
Input: userId, token, expiredAt
Output: boolean

🔹 sendResetEmail
Mục đích: Tạo link reset và gửi email đến người dùng.
Module: Email Module
Input: email, resetLink
Output: boolean

🔹 logAction
Mục đích: Ghi log thao tác yêu cầu đặt lại mật khẩu (FR-55).
Module: Logging Module
Input: userId, action
Output: void



Mã
Nội dung
MS_01
"Vui lòng nhập địa chỉ email!"
MS_02
"Định dạng email không hợp lệ!"
MS_03
"Email không tồn tại trong hệ thống!"
MS_04
"Hệ thống đã gửi email hướng dẫn đặt lại mật khẩu. Vui lòng kiểm tra hộp thư!"
MS_05
"Gửi email thất bại, vui lòng thử lại!"




CHƯƠNG 5. XÂY DỰNG VÀ KIỂM THỬ HỆ THỐNG
Lựa chọn xây dựng và kiểm thử các chức năng đáp ứng cho ít nhất 20% yêu cầu quan trọng nhất của hệ thống và phải chỉ rõ Ai-làm gì tương ứng với chương này trong bảng phân công (mục 1.7)
Kết quả xây dựng module/nhóm chức năng
….
….
nKiểm thử hệ thống
Tổng quan về kiểm thử phần mềm
….
….
Mô hình kiểm thử được áp dụng trong đề tài
Kế hoạch kiểm thử
Kết quả kiểm thử

CHƯƠNG 6. KẾT LUẬN
Đánh giá chất lượng phần mềm
Ưu điểm & Nhược điểm của đề tài
Hướng phát triển
….

TÀI LIỆU THAM KHẢO
PHỤ LỤC A: QUY TẮC ĐẶT TÊN
PHỤ LỤC B: CÁC BIỂU MẪU, TÀI LIỆU THU THẬP ĐƯỢC
PHỤ LỤC C: CÁC BIÊN BẢN TIẾN ĐỘ
PHỤ LỤC D: CÁC BIÊN BẢN RÀ SOÁT KỸ THUẬT
PHỤ LỤC E: HỒ SƠ TOÀN BỘ DỰ ÁN
Hồ sơ khảo sát
Hồ sơ phân tích
Hồ sơ thiết kế
Mã nguồn
Hồ sơ kiểm thử
