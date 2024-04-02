import Image from "next/image";
import { Fragment } from "react";
import { useMediaQuery } from "react-responsive";

export default function Page2({ activePage }: { activePage: boolean }) {
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  return (
    <Fragment>
      {isMobile ? (
         <div className="max-w-[400px] h-screen mx-auto pt-40">
          <div className="flex flex-wrap justify-center align-middle">
            <div className="flex flex-col justify-evenly">
              <div
                className={`flex flex-col justify-center text-2xl font-semibold tracking-widest ${activePage ? "animate-slide-up" : ""}`}
              >
                <div className="flex justify-center">
                  <div className="my-1 text-transparent bg-clip-text bg-gradient-to-r from-f5yellowgreen-200 to-f5green-300">
                    여러 기업의 채용 공고
                  </div>
                  <div className="my-1 text-f5black-400">를</div>
                </div>
                <div className="my-1 text-f5black-400 flex justify-center">
                  한 번에 볼 수 있습니다
                </div>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <div className="w-[100%] h-[100%]">
          <div className={`flex flex-wrap justify-start align-middle pt-28 pl-60  ${activePage ? "animate-slide-up" : ""}`}>
            <div className="flex flex-col justify-evenly">
              <div
                className={`flex flex-col justify-start text-4xl font-semibold tracking-widest`}
              >
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
                src="/images/companyLogo2.png"
                alt="companyLogo"
                width={410}
                height={269}
                className={`mt-16 ml-12`}
              />
            </div>
          </div>
        </div>
      )}
    </Fragment>
  );
}
