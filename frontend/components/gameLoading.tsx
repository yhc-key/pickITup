import Image from "next/image";

export default function GameLoading() {
  return (
    <div className="flex mb:flex-col min-h-[450px]  mx-auto w-full mb:w-[350px] items-center justify-center">
      <div className="flex justify-cenetr mr-20 mb:mr-0">
        <Image
          src="/images/ghost.png"
          alt="ghost"
          width={240}
          height={240}
          className=" animate-[bounce_2s_ease-in-out_infinite] mt-28"
        ></Image>
      </div>
      <div className="flex flex-col justify-start items-start mb:justify-center mb:items-center">
        <div className="mb:text-2xl text-3xl font-semibold mb-10">
          현재 <b className="text-f5green-300">서비스 준비중</b>입니다.
        </div>
        <div className="text-lg mb:text-base">이용에 불편을 드려 죄송합니다.</div>
        <div className="text-lg mb:text-base">
          빠른 시일 내에 이용하실 수 있도록 노력하겠습니다.
        </div>
      </div>
    </div>
  );
}
