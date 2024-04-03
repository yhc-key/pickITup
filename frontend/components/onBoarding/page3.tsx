import Image from "next/image";
import { Fragment } from "react";
import { useMediaQuery } from "react-responsive";

export default function Page3({ activePage }: { activePage: boolean }) {
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  return (
    <Fragment>
      {isMobile ? (
        <div className="max-w-[400px] h-screen pt-20 mx-auto">
          <div className="h-[80%] my-auto">
            <div className="flex flex-wrap justify-center align-middle">
              <div className="flex flex-col justify-evenly items-center">
                <div
                  className={`flex flex-col mb-2 justify-start text-xl font-semibold tracking-widest ${activePage ? "animate-slide-up" : ""}`}
                >
                  <div className="flex justify-center">
                    <div className="my-1 text-transparent bg-clip-text bg-gradient-to-r from-f5yellowgreen-200 to-f5green-300">
                      선호하는 기술 스택
                    </div>
                    <div className="my-1 text-f5black-400">에 맞춰</div>
                  </div>
                  <div className="my-1 text-f5black-400 flex justify-center">
                    사용자 맞춤 채용 공고를 추천합니다
                  </div>
                </div>
              </div>
              <Image
                src="/images/phoneScreen.png"
                alt="phoneScreen"
                width={200}
                height={200}
                className="mt-20"
              />
            </div>
          </div>
        </div>
      ) : (
        <div className="w-[100%] h-[100%]">
          <div
            className={`flex flex-wrap justify-start align-middle pt-28 pl-52  ${activePage ? "animate-slide-up" : ""}`}
          >
            <div className="flex flex-col justify-evenly">
              <div
                className={`flex flex-col ml-12 mb-2 justify-start text-4xl font-semibold tracking-widest`}
              >
                <div className="flex">
                  <div className="my-1 text-transparent bg-clip-text bg-gradient-to-r from-f5yellowgreen-200 to-f5green-300">
                    선호하는 기술 스택
                  </div>
                  <div className="my-1 text-f5black-400">에 맞춰</div>
                </div>
                <div className="my-1 text-f5black-400">
                  사용자 맞춤 채용 공고를 추천합니다
                </div>
              </div>
              <Image
                src="/images/techStack.png"
                alt="companyLogo"
                width={472}
                height={240}
                className="ml-20"
              />
            </div>
            <Image
              src="/images/phoneScreen.png"
              alt="phoneScreen"
              width={370}
              height={370}
              className="mt-20"
            />
          </div>
        </div>
      )}
    </Fragment>
  );
}
