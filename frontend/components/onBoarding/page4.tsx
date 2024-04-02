import Image from "next/image";
import { Fragment } from "react";
import { useMediaQuery } from "react-responsive";

export default function Page4({ activePage }: { activePage: boolean }) {
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  return (
    <Fragment>
      {isMobile ? (
        <div className="max-w-[400px] h-screen pt-40 mx-auto">
          <div className="flex flex-wrap justify-center align-middle">
            <div className="flex justify-evenly">
              <div className={`flex flex-col justify-start text-xl font-semibold tracking-widest ${activePage ? "animate-slide-up" : ""}`}>
                <div className="flex justify-center">
                  <div className="my-1 text-transparent bg-clip-text bg-gradient-to-r from-f5yellowgreen-200 to-f5green-300">
                    간편한 이력 관리
                  </div>
                  <div className="my-1 text-f5black-400">를 위하여</div>
                </div>
                <div className="my-1 text-f5black-400 flex justify-center">
                  유사 문항을 함께 관리할 수 있는
                </div>
                <div className="my-1 text-f5black-400 flex justify-center">
                  기능을 제공합니다
                </div>
                <div></div>
              </div>
            </div>
            <Image
              src="/images/resume.png"
              alt="resume"
              width={278}
              height={228}
              className="flex justify-center mt-20 ml-6"
            />
          </div>
        </div>
      ) : (
        <div className="w-[100%] h-[100%]">
          <div className={`flex flex-wrap justify-start align-middle pt-32 pl-40 ${activePage ? "animate-slide-up" : ""}`}>
            <div className="flex justify-evenly">
              <div className={`flex flex-col justify-start text-4xl font-semibold tracking-widest`}>
                <div className="flex">
                  <div className="my-1 ml-3 text-transparent bg-clip-text bg-gradient-to-r from-f5yellowgreen-200 to-f5green-300">
                    간편한 이력 관리
                  </div>
                  <div className="my-1 text-f5black-400">를 위하여</div>
                </div>
                <div className="my-1 ml-3 text-f5black-400">
                  유사 문항을 함께 관리할 수 있는 기능을 제공합니다
                </div>
                <div></div>
              </div>
            </div>
            <Image
              src="/images/resume.png"
              alt="resume"
              width={378}
              height={328}
              className="mt-28"
            />
          </div>
        </div>
      )}
    </Fragment>
  );
}
