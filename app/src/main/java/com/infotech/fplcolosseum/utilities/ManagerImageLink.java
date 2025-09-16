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
        managerImagerLinkIDs.put(1458142L, "1cn4746PciHYRgxa7oxTOohTdG8nmg509"); // Arman Naiem
        managerImagerLinkIDs.put(6312306L, "1nbKU8dZ5KMJdrU_8OFBtlhQboYUCynZM"); // TOMAL VAI(Imrul Kayes)
        managerImagerLinkIDs.put(4970081L, "1Er1znSbCHEzdRYfAxQjGDQfYpGsu8ubz"); // REFAT VAI
        managerImagerLinkIDs.put(7994539L, "1iXAT7wPl57mHxnB3mBgIdW_o3ztM2lKS"); // IMRANUR RAHMAN
        managerImagerLinkIDs.put(4507255L, "1ZzjcPhRYm0kHkJBgYbKKagbgOqJoRaXl"); // MAHIM
        managerImagerLinkIDs.put(2996620L, "1jgLX803aKHGj49BCOb4sS8bw8vvAXhoA"); // PARVEZ VAI
        managerImagerLinkIDs.put(3172197L, "1kTEGlIygTv9lSUzhr3cTTyeEzkvsAD6B"); // ARKA VAI
        managerImagerLinkIDs.put(6872521L, "13aJTT5pidpFluGQ5chS5reh55F-vJNu2"); // Mostafa Lutfi
        managerImagerLinkIDs.put(7331899L, "14xeFRSlU_vhU2t2NZk8AA9DfJ2MDxm7y"); // Rezoan Heera
        managerImagerLinkIDs.put(7370433L, "1eHoDA04uxOj6RMBcbveMYSGsME8tZbpq"); // Jony Vai EEE
        managerImagerLinkIDs.put(4164112L, "19AAieLG3lsDQ4oanuCJib3QdHx5waock"); // Masud Vai Srbd
        managerImagerLinkIDs.put(5119279L, "1stfewHEGSjFGmqz-z0Z05vDfXIV46L-E"); // Zakariya Vai
        managerImagerLinkIDs.put(5608513L, "1gBdgGs7nw0FMLke3GAGEShulRY7FunHM"); // Mahfuzur Vai
        managerImagerLinkIDs.put(3711460L, "1P-4NlcXvBYUUlBNQE8MygIsHDGQboUtW"); // Rafsan Srbd
        managerImagerLinkIDs.put(3229009L, "1g4F31GuYL4oTpdCepyd7QCRkU5ng5am1"); // Asish Vai Srbd
        managerImagerLinkIDs.put(3020130L, "1ZqifEQvpm-XJxKOIzdgJY5paqldK8FvM"); // Riman Vai
        managerImagerLinkIDs.put(3441946L, "1JbcAOiBWWGtYSEgfHJ-Tm1k8-s39jtRN"); // Golam Kayas
        managerImagerLinkIDs.put(5016777L, "1KbaP2tJoM_qmE2q8PSolvmnH7Q5qq5rm"); // Amit Biswas Vai
        managerImagerLinkIDs.put(3136959L, "1U9VNkSlqMAWwsw1PCi3Jru_c3ADcoNN3"); // Asif Sazzad Vai
        managerImagerLinkIDs.put(5185478L, "1zMp7G_B9CXbUri7mDIPYNU20WP0_5YMa"); // Imtiaz EEE
        managerImagerLinkIDs.put(2996945L, "1hrZoCVkMMfuvvll8k1zj91vTWVA-xMpO"); // Soumitra Vai Srbd
        managerImagerLinkIDs.put(2558669L, "1FUsXFLJteJkjTEA_9KFUMEpCe0MiNgtW"); // Tariq Vai Srbd
        managerImagerLinkIDs.put(2240313L, "1ZgjUwx0tLlMtCptQjSudrHgdSv4UHPTN"); // Saiful Vai
        managerImagerLinkIDs.put(903107L, "13dT0JJpV5RiNSEctJuyr1I9edoM2VB21"); // Saleh Ahmed Efad Vai
        managerImagerLinkIDs.put(6809608L, "1hTRtThth2Gx1t-IzbpxFszoIE9QfIN15"); // Sayem
        managerImagerLinkIDs.put(6680701L, "1PJQZXc1E2TlORHfueEZk003cVaIeJ9Nm"); // Abdullah Al Mamun (Intrepid_XI)
        managerImagerLinkIDs.put(3311509L, "1NUigaYOkobdovD9EoAlykSorFYSz6V1B"); // AK Saddam
        managerImagerLinkIDs.put(125032L, "11I-NsF5Pcg7TgfL1N9Bwzvr8SsNTpy9g"); // Moyeen Shuvo
        managerImagerLinkIDs.put(6666201L, "1DH-PsJWgHLA_8vgz4Om5WLJKN-H3f8Rf"); // AL Noman
        managerImagerLinkIDs.put(1290582L, "1pLP1wgRy95Gr_DoiT0OLvqjZVB8xk12A"); // Zilani SRBD
        managerImagerLinkIDs.put(1187481L, "12x_567bMOQt3Rx_Jup6tOJE-NUzyyWDc"); // MAHABUB MOYUKH RISHAD
        managerImagerLinkIDs.put(2359124L, "1pWkdm7kq_3SQXNjVeqwIf28MqSVcYx0n"); // Jahangir vai
        managerImagerLinkIDs.put(934024L, "1eLMZS_ukWQj4Lf23asHzsxMoCxm8rg67"); // Zishan Frnd
        managerImagerLinkIDs.put(3527039L, "1eYMJ2gPmYNdroX48xJM54fIbnMRJoS60"); // Shawon Vai
        managerImagerLinkIDs.put(356954L, "1tb2AVFcj2WEgxb-D9uvQesQYN2yIoPwq"); // Samiul Islam Sami
        managerImagerLinkIDs.put(3061079L, "15A-uiV_B9rcV4FbpA-INWC5z9utnazCn"); // Abir Vai
        managerImagerLinkIDs.put(570687L, "1s0oLinHV-ZT4GwDVgY27Cvm5QK_iHfJz"); // Khalid Al Hasan
        managerImagerLinkIDs.put(2055946L, "1xPTO7fCi2SDdycTfdaksKN253F0V6nLU"); // Azfer Vai Srbd
        managerImagerLinkIDs.put(349713L, "1Zsu4_6O5r9sSkLrBQCD_Q4uXEwmr350H"); // Monirul Islam
        managerImagerLinkIDs.put(2634722L, "1aBl-sAq6qejCSt9MXrL3XPFa9o3odlbe"); // Shubho Saha
        managerImagerLinkIDs.put(1181283L, "1Os9qUNToA2_g_qtQKc5QovFFa6EOUuRK"); // Mahmudul Hasan
        managerImagerLinkIDs.put(5680929L, "1fYCqoRoAjrXVZtqK85eZozs4Z-iDuHfW"); // Raihan Rabbi
        managerImagerLinkIDs.put(31773L, "1tx0_vTd4VAaTqPAsUTywhLayoMsMzQhl"); // Narayan Vai SrBd
        managerImagerLinkIDs.put(4858087L, "11_Ip9i14aUpBoQAafdKXveHJPZkqIh2-"); // Palash
        managerImagerLinkIDs.put(31893L, "1XQdsSsn8UcpTbPRQVRJfw5IKC4JHTqAy"); // Talha SRBD
        managerImagerLinkIDs.put(2156745L, "17jSjXnzrz8bgkoYBbTwhzlGjvujsbsdK"); // Riadhul Islam Riad(Abu Sina Riad)
        managerImagerLinkIDs.put(41431L, "14k4nPDSs8N5aQnWX0b9FTeNJt2dZGd54"); // Abdullah Al Mamun
        managerImagerLinkIDs.put(2417947L, "17hB2-IWUFAWyMD_beTNBHXf7tVFTK9ix"); // Abu Sifat Chanchal
        managerImagerLinkIDs.put(1371758L, "1HnP9KTFofjtHNB-2FO71rE0BZonIW6ld"); // Niaze Vai SRBD
        managerImagerLinkIDs.put(4113L, "1JMU4qjonrtANZrW0gB2Sa8UCzQAdb-M8"); // Maruf Chowdhury
        managerImagerLinkIDs.put(592434L, "1ihNWPDlvddGhELKXV31fdIAr3eR6bxoN"); // Faiyaz Saif
        managerImagerLinkIDs.put(176404L, "139ip2aptuas73iIXIYQnv_JoAlvoU-Nz"); // Ashik Ahmed Topon
        managerImagerLinkIDs.put(4810043L, "1B14Xq_zNp7kiuwggtYwWEXGTNH3oB9Lq"); // Ratul Vai
        managerImagerLinkIDs.put(5696106L, "10qlCrZBcFQTKgj9O-199XBI_h1vnaueh"); // SM Rezai Rabbe
        managerImagerLinkIDs.put(380073L, "1m_3_p0qwinQ0hgUHoQooxbeeThZEeySf"); // Hasnain Sarker
        managerImagerLinkIDs.put(8174543L, "1gO0SmMXP3UEiTcp43MALevnj2AnYgu4z"); // Riyadh Dhrubo
        managerImagerLinkIDs.put(5646314L, "1ZpMadKSG9i15YS5Oq5O5NlA9W7eMR624"); // Ashraf Rony Vai
        managerImagerLinkIDs.put(4747182L, "1I_O2neqUvSPUg1hHXWT56MVH_uahHT6R"); //Shoumic Shahid
        managerImagerLinkIDs.put(2950776L, "1wKZLkzuhu-Q_8gnC2NCYUQABU3gu_2ob"); //Zayed Hassan
        managerImagerLinkIDs.put(1220578L, "1kZVACIvp6bXenIX2bWIU6EAo4v_EKSoT"); //Mehedi Arman Shawon
        managerImagerLinkIDs.put(2814007L, "1TGpEenIqWTXiw8ulvRizLh0ckVv8VR1W"); //Hasin Mahtab Farabi
        managerImagerLinkIDs.put(7072392L, "17cNJV76fhfOzoNF2qbRCoJ0wjMhWUGR8"); //Anup Kumer Paul
        managerImagerLinkIDs.put(3712954L, ""); //Talha Zinan
        managerImagerLinkIDs.put(6854358L, "1akg8FPc6Fru9zlnEi7pK2FfZbGjgvh6U"); //Md Nahid Hasan Rabbi
    }
}
