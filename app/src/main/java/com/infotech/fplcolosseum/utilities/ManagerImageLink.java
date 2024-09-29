package com.infotech.fplcolosseum.utilities;

import com.infotech.fplcolosseum.features.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.features.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.features.homepage.models.fixture.OpponentData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekEvent;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekStaticDataModel;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.Player_Type;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.TeamData;
import com.infotech.fplcolosseum.features.login.models.UserResponseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerImageLink {
    public static final String FUNCTION_KEY = "db059d47-8b44-476a-9dfc-509bceb87bee";
    //image link

    public static Map<Long, String> managerImagerLinkIDs = new HashMap<>();
    static {
        // Initialize the map with some data
        managerImagerLinkIDs.put(4472464L, "1cn4746PciHYRgxa7oxTOohTdG8nmg509"); // Arman Naiem
        managerImagerLinkIDs.put(6878053L, "1nbKU8dZ5KMJdrU_8OFBtlhQboYUCynZM"); // TOMAL VAI
        managerImagerLinkIDs.put(3077689L, "1Er1znSbCHEzdRYfAxQjGDQfYpGsu8ubz"); // REFAT VAI
        managerImagerLinkIDs.put(5753641L, "1iXAT7wPl57mHxnB3mBgIdW_o3ztM2lKS"); // IMRANUR RAHMAN
        managerImagerLinkIDs.put(3462801L, "1ZzjcPhRYm0kHkJBgYbKKagbgOqJoRaXl"); // MAHIM
        managerImagerLinkIDs.put(5896833L, "1jgLX803aKHGj49BCOb4sS8bw8vvAXhoA"); // PARVEZ VAI
        managerImagerLinkIDs.put(2443818L, "1kTEGlIygTv9lSUzhr3cTTyeEzkvsAD6B"); // ARKA VAI
        managerImagerLinkIDs.put(6926322L, "13aJTT5pidpFluGQ5chS5reh55F-vJNu2"); // Mostafa Lutfi
        managerImagerLinkIDs.put(6155756L, "14xeFRSlU_vhU2t2NZk8AA9DfJ2MDxm7y"); // Rezoan Heera
        managerImagerLinkIDs.put(6343622L, "1eHoDA04uxOj6RMBcbveMYSGsME8tZbpq"); // Jony Vai EEE
        managerImagerLinkIDs.put(7402529L, "19AAieLG3lsDQ4oanuCJib3QdHx5waock"); // Masud Vai Srbd
        managerImagerLinkIDs.put(4562836L, "1stfewHEGSjFGmqz-z0Z05vDfXIV46L-E"); // Zakariya Vai
        managerImagerLinkIDs.put(3635765L, "1gBdgGs7nw0FMLke3GAGEShulRY7FunHM"); // Mahfuzur Vai
        managerImagerLinkIDs.put(5152775L, "1P-4NlcXvBYUUlBNQE8MygIsHDGQboUtW"); // Rafsan Srbd
        managerImagerLinkIDs.put(4287551L, "1g4F31GuYL4oTpdCepyd7QCRkU5ng5am1"); // Asish Vai Srbd
        managerImagerLinkIDs.put(3020130L, "1ZqifEQvpm-XJxKOIzdgJY5paqldK8FvM"); // Riman Vai
        managerImagerLinkIDs.put(2751478L, "1JbcAOiBWWGtYSEgfHJ-Tm1k8-s39jtRN"); // Golam Kayas
        managerImagerLinkIDs.put(6716555L, "1KbaP2tJoM_qmE2q8PSolvmnH7Q5qq5rm"); // Amit Biswas Vai
        managerImagerLinkIDs.put(3136959L, "1U9VNkSlqMAWwsw1PCi3Jru_c3ADcoNN3"); // Asif Sazzad Vai
        managerImagerLinkIDs.put(5145565L, "1zMp7G_B9CXbUri7mDIPYNU20WP0_5YMa"); // Imtiaz EEE
        managerImagerLinkIDs.put(5368117L, "1hrZoCVkMMfuvvll8k1zj91vTWVA-xMpO"); // Soumitra Vai Srbd
        managerImagerLinkIDs.put(5023580L, "1FUsXFLJteJkjTEA_9KFUMEpCe0MiNgtW"); // Tariq Vai Srbd
        managerImagerLinkIDs.put(6808414L, "1ZgjUwx0tLlMtCptQjSudrHgdSv4UHPTN"); // Saiful Vai
        managerImagerLinkIDs.put(3675243L, "13dT0JJpV5RiNSEctJuyr1I9edoM2VB21"); // Saleh Ahmed Efad Vai
        managerImagerLinkIDs.put(4591570L, "1hTRtThth2Gx1t-IzbpxFszoIE9QfIN15"); // Sayem
        managerImagerLinkIDs.put(3720728L, "1PJQZXc1E2TlORHfueEZk003cVaIeJ9Nm"); // Abdullah Al Mamun (Intrepid_XI)
        managerImagerLinkIDs.put(4024963L, "1NUigaYOkobdovD9EoAlykSorFYSz6V1B"); // AK Saddam
        managerImagerLinkIDs.put(2442996L, "11I-NsF5Pcg7TgfL1N9Bwzvr8SsNTpy9g"); // Moyeen Shuvo
        managerImagerLinkIDs.put(4664346L, "1DH-PsJWgHLA_8vgz4Om5WLJKN-H3f8Rf"); // AL Noman
        managerImagerLinkIDs.put(1290582L, "1pLP1wgRy95Gr_DoiT0OLvqjZVB8xk12A"); // Zilani SRBD
        managerImagerLinkIDs.put(1187481L, "12x_567bMOQt3Rx_Jup6tOJE-NUzyyWDc"); // MAHABUB MOYUKH RISHAD
        managerImagerLinkIDs.put(215451L, "1pWkdm7kq_3SQXNjVeqwIf28MqSVcYx0n"); // Jahangir vai
        managerImagerLinkIDs.put(2158690L, "1eLMZS_ukWQj4Lf23asHzsxMoCxm8rg67"); // Zishan Frnd
        managerImagerLinkIDs.put(2119875L, "1eYMJ2gPmYNdroX48xJM54fIbnMRJoS60"); // Shawon Vai
        managerImagerLinkIDs.put(118578L, "1tb2AVFcj2WEgxb-D9uvQesQYN2yIoPwq"); // Samiul Islam Sami
        managerImagerLinkIDs.put(2113217L, "15A-uiV_B9rcV4FbpA-INWC5z9utnazCn"); // Abir Vai
        managerImagerLinkIDs.put(2422645L, "1s0oLinHV-ZT4GwDVgY27Cvm5QK_iHfJz"); // Khalid Al Hasan
        managerImagerLinkIDs.put(445971L, "1xPTO7fCi2SDdycTfdaksKN253F0V6nLU"); // Azfar Vai Srbd
        managerImagerLinkIDs.put(2097590L, "1Zsu4_6O5r9sSkLrBQCD_Q4uXEwmr350H"); // Monirul Islam
        managerImagerLinkIDs.put(2989676L, "1aBl-sAq6qejCSt9MXrL3XPFa9o3odlbe"); // Shubho Saha
        managerImagerLinkIDs.put(613278L, "1Os9qUNToA2_g_qtQKc5QovFFa6EOUuRK"); // Mahmudul Hasan
        managerImagerLinkIDs.put(4134591L, "1WcdpESuHaGqXOOC5ts97pnQksPQfpvHS"); // Raihan Rabbi
        managerImagerLinkIDs.put(2640203L, "1tx0_vTd4VAaTqPAsUTywhLayoMsMzQhl"); // Narayan Vai SrBd
        managerImagerLinkIDs.put(2741832L, "11_Ip9i14aUpBoQAafdKXveHJPZkqIh2-"); // Palash
        managerImagerLinkIDs.put(162067L, "1XQdsSsn8UcpTbPRQVRJfw5IKC4JHTqAy"); // Talha SRBD
        managerImagerLinkIDs.put(2386325L, "17jSjXnzrz8bgkoYBbTwhzlGjvujsbsdK"); // Abu Sina Riad
        managerImagerLinkIDs.put(41431L, "14k4nPDSs8N5aQnWX0b9FTeNJt2dZGd54"); // Abdullah Al Mamun
        managerImagerLinkIDs.put(2042057L, "17hB2-IWUFAWyMD_beTNBHXf7tVFTK9ix"); // Abu Sifat Chanchal
        managerImagerLinkIDs.put(702288L, "1HnP9KTFofjtHNB-2FO71rE0BZonIW6ld"); // Niaze Vai SRBD
        managerImagerLinkIDs.put(4113L, "1JMU4qjonrtANZrW0gB2Sa8UCzQAdb-M8"); // Maruf Chowdhury
        managerImagerLinkIDs.put(88365L, "1ihNWPDlvddGhELKXV31fdIAr3eR6bxoN"); // Faiyaz Saif
        managerImagerLinkIDs.put(2100889L, "139ip2aptuas73iIXIYQnv_JoAlvoU-Nz"); // Ashik Ahmed Topon
        managerImagerLinkIDs.put(2408612L, "1B14Xq_zNp7kiuwggtYwWEXGTNH3oB9Lq"); // Ratul Vai
        managerImagerLinkIDs.put(6672008L, "10qlCrZBcFQTKgj9O-199XBI_h1vnaueh"); // SM Rezai Rabbe
        managerImagerLinkIDs.put(4470366L, "1m_3_p0qwinQ0hgUHoQooxbeeThZEeySf"); // Hasnain Sarker
        managerImagerLinkIDs.put(8174543L, "1gO0SmMXP3UEiTcp43MALevnj2AnYgu4z"); // Riyadh Dhrubo
        managerImagerLinkIDs.put(6990354L, "1ZpMadKSG9i15YS5Oq5O5NlA9W7eMR624"); // Ashraf Rony Vai

    }
}