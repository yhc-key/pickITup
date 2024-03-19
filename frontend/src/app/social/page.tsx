"use client"
import Image from 'next/image'
import Link from "next/link";
function Social(){
    const requsetKakaoLogin = () => {
        window.location.href = 'https://spring.pickitup.online/oauth2/authorization/kakao';
    }
    const requsetNaverLogin = () => {
        window.location.href = '#';//url 집어넣기
    }
    const requsetGoogleLogin = () => {
        window.location.href = '#';//url 집어넣기
    }
    
    
    const messages = ["픽잇업이 제공하는 서비스를", "하나의 계정으로 모두 이용할 수 있습니다!"];
    return (
        <div className="flex flex-col items-center w-full h-[600px]">
            <div className="h-[15vh]"></div>
            <div className="w-[40vw] h-[50vh] grid place-items-center">
                <div className="flex flex-col justify-center items-center">
                    {messages.map((line, index) => (
                        <div className="my-1 text-2xl font-bold" key={index}>
                            {line}
                        </div>
                    ))}
                </div>
                <div className="w-[25vw] h-[5vh] py-5 my-2"></div>
                <button onClick={requsetKakaoLogin} className="w-[25vw] h-[5vh] py-5 my-4 flex items-center justify-start rounded-[10px] bg-[#FFEC00] whitespace-pre font-bold">
                    <div className="w-[8vh]"></div>
                    <Image src="/images/kakaoLogo.png" width={40} height={40} alt="kakaoLogo"/>
                    <div className="w-[1.8vw]"></div>카카오 계정으로 로그인</button>
                <button onClick={requsetNaverLogin} className="w-[25vw] h-[5vh] py-5 my-4 flex items-center justify-start rounded-[10px] text-white bg-[#03C75A] whitespace-pre font-bold">
                    <div className="w-[8vh]"></div>
                    <Image src="/images/naverLogo.png" width={40} height={40} alt="naverLogo"/>
                    <div className="w-[1.8vw]"></div>네이버 계정으로 로그인</button>
                <button onClick={requsetGoogleLogin} className="w-[25vw] h-[5vh] py-5 my-4 flex items-center justify-start rounded-[10px] border border-[#d9d9d9] whitespace-pre font-bold">
                    <div className="w-[9vh]"></div>
                    <Image src="/images/googleLogo.png" width={21} height={21} alt="googleLogo"/>
                    <div className="w-[2.5vw]"></div>구글 계정으로 로그인</button>
                <Link href="/login" className="w-[25vw] h-[5vh] py-5 my-4 flex items-center justify-start rounded-[10px] border border-[#d9d9d9] whitespace-pre font-bold">
                    <div className="w-[8.8vh]"></div>
                    <Image src="/images/pickITupLogo.png" width={24} height={21.84} alt="pickITupLogo"/>
                    <div className="w-[2.5vw]"></div>pick IT up 로그인</Link>
            </div>
        </div>
    )
}
export default Social;