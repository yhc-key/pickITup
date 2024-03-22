import Image from "next/image";

export default function Page2() {
  return (
    <div className="w-[100%] h-[100%]">
      <div className="flex flex-wrap justify-start align-middle ml-60">
        <div className="flex flex-col justify-evenly">
          <div className="flex flex-col justify-start text-4xl font-semibold tracking-widest">
            <div className="flex">
            <div className="my-1 ml-3 text-transparent bg-clip-text bg-gradient-to-r from-f5yellowgreen-200 to-f5green-300">
              여러 기업의 채용 공고
            </div>
            <div className="my-1 text-f5black-400">를</div>
            </div>
            <div className="my-1 ml-3 text-f5black-400">
              한 번에 볼 수 있습니다
            </div>
          </div>
          <Image
            src="/images/companyLogo.png"
            alt="companyLogo"
            width={300}
            height={202}
            className="mt-16 ml-20"
          />
        </div>
        <div>
        <Image
            src="/images/page2.png"
            alt="laptop2"
            width={530}
            height={324}
            className="ml-14"
          />
        </div>
      </div>
    </div>
  );
}
