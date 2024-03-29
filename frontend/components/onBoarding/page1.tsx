import Image from "next/image";

export default function Page1({ activePage }: { activePage: boolean }) {
  return (
    <div className="w-[100%] h-[100%] overflow-hidden">
      <div className="flex flex-wrap justify-start pt-20 pl-60">
        <Image
          src="/images/onBoarding.png"
          alt="온보딩이미지"
          width={220}
          height={220}
        />
        <div className="flex flex-col justify-evenly">
          <div className="flex justify-start font-semibold tracking-widest text-7xl">
            <div className="mx-3 my-1 text-f5black-400">pick</div>
            <div className="mx-3 my-1 text-transparent bg-clip-text bg-gradient-to-r from-f5yellowgreen-200 to-f5green-300 ">
              IT
            </div>
            <div className="my-1 ml-3 text-f5black-400">up</div>
          </div>
          <div className="flex flex-col">
          <div className="ml-4 text-lg text-f5black-400 mb-1">
          pick IT up은  &nbsp;<b className="text-f5green-300">선호하는 기술 스택</b>을 통해 채용 공고를 추천받고
          </div>
          <div className="ml-4 text-lg text-f5black-400">
         취업을 준비할 수 있는 서비스 입니다.
          </div>
          </div>
        </div>
      </div>
    </div>
  );
}
