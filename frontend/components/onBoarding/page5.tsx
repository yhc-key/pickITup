import Image from "next/image";
import { Fragment } from "react";
import { useMediaQuery } from "react-responsive";

export default function Page5({ activePage }: { activePage: boolean }) {
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  return (
    <Fragment>
      {isMobile ? (
        <div className="max-w-[400px] h-[750px] pt-20 mx-auto">
          <div className="flex flex-wrap justify-center align-middle">
            <div className="flex flex-col justify-evenly">
              <div
                className={`flex justify-center  ${activePage ? "animate-slide-up" : ""}`}
              >
                <div
                  className={`flex flex-col justify-center items-center text-xl font-semibold tracking-widest`}
                >
                  <div className="my-1 text-f5black-400">
                    스피드 퀴즈, OX게임을 즐기며
                  </div>
                  <div className="flex">
                    <div className="my-1 text-transparent bg-clip-text bg-gradient-to-r from-f5yellowgreen-200 to-f5green-300">
                      기술 면접 대비
                    </div>
                    <div className="my-1 text-f5black-400">를 해보세요!</div>
                  </div>
                </div>
                <Image
                  src="/images/gameMachine.png"
                  alt="gameMachine"
                  width={60}
                  height={60}
                />
              </div>
              <div className="flex flex-col items-center justify-center">
                <Image
                  src="/images/oxIntroPage.png"
                  alt="oxIntroPage"
                  width={270}
                  height={147}
                  className="mt-10 py-5 rounded-xl shadow-lg"
                />
                <Image
                  src="/images/speedIntroPage.png"
                  alt="speedIntroPage"
                  width={270}
                  height={147}
                  className="mt-10 py-5 rounded-xl shadow-lg"
                />
              </div>
            </div>
          </div>
        </div>
      ) : (
        <div className="w-[100%] h-[100%]">
          <div className={`flex flex-wrap justify-center align-middle pt-16 ${activePage ? "animate-slide-up" : ""}`}>
            <div className="flex flex-col justify-evenly">
              <div className="flex justify-center ml-10">
                <div
                  className={`flex flex-col justify-center items-center text-4xl font-semibold tracking-widest`}
                >
                  <div className="my-2 ml-3 text-f5black-400">
                    스피드 퀴즈, OX게임을 즐기며
                  </div>
                  <div className="flex">
                    <div className="my-1 ml-3 text-transparent bg-clip-text bg-gradient-to-r from-f5yellowgreen-200 to-f5green-300">
                      기술 면접 대비
                    </div>
                    <div className="my-1 text-f5black-400">를 해보세요!</div>
                  </div>
                </div>
                <Image
                  src="/images/gameMachine.png"
                  alt="gameMachine"
                  width={200}
                  height={200}
                />
              </div>
              <div className="flex">
                <Image
                  src="/images/oxIntroPage.png"
                  alt="oxIntroPage"
                  width={500}
                  height={258}
                  className="mt-10 py-5 rounded-xl mr-10 shadow-lg"
                />
                <Image
                  src="/images/speedIntroPage.png"
                  alt="speedIntroPage"
                  width={500}
                  height={258}
                  className="mt-10 py-5 rounded-xl shadow-lg"
                />
              </div>
            </div>
          </div>
        </div>
      )}
    </Fragment>
  );
}
