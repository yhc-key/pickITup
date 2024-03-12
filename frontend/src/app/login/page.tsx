import Image from 'next/image'
export default function Login(){
    const messages = ["픽잇업이 제공하는 서비스를", "하나의 계정으로 모두 이용할 수 있습니다!"];
    return (
        <div className="grid place-items-center w-full h-[600px]">
            <div className="absolute top-1/4 w-[25vw] h-[50vh]">
                <div className="grid place-items-center">
                    {messages.map((line, index) => (
                        <div className="my-1" key={index}>
                            {line}
                        </div>
                    ))}
                </div>
                <div className="w-full h-[5vh]"></div>
                <div className="w-full h-[5vh]"></div>
                <div className="w-full h-[5vh] py-5 my-4 flex items-center justify-start rounded-[10px] bg-[#FFEC00] whitespace-pre">
                    <Image src="/images/kakaoLogo.png" width={40} height={40} alt="kakaoLogo" className="absolute left-[15%]" /><div className='absolute left-[30%]'>카카오 계정으로 로그인</div></div>
                <div className="w-full h-[5vh] py-5 my-4 flex items-center justify-start rounded-[10px] text-white bg-[#03C75A] whitespace-pre">
                    <Image src="/images/naverLogo.png" width={40} height={40} alt="naverLogo" className="absolute left-[15%]" /><div className='absolute left-[30%]'>네이버 계정으로 로그인</div></div>
                <div className="w-full h-[5vh] py-5 my-4 flex items-center justify-start rounded-[10px] border border-[#f5f5f5] whitespace-pre">
                    <Image src="/images/googleLogo.png" width={21} height={21} alt="googleLogo" className="absolute left-[18%]" /><div className='absolute left-[30%]'>구글 계정으로 로그인</div></div>
                <div className="w-full h-[5vh] py-5 my-4 flex items-center justify-start rounded-[10px] border border-[#f5f5f5] whitespace-pre">
                    <Image src="/images/pickItupLogo.png" width={24} height={21.84} alt="pickITupLogo" className="absolute left-[17%]"/><div className='absolute left-[30%]'>pick IT up 로그인</div></div>
            </div>
        </div>
    )
}