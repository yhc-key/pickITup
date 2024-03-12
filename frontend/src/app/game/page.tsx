import Image from "next/image";
import Link from "next/link";

import { IoHelpCircleSharp } from "react-icons/io5";

import Tooltip from "../../../components/Tooptip";

export default function GamePage() {
  const gameInfo: string =
    "한 게임은 총 10문제로 구성되어 있으며 \n한 문제당 제한시간은 10초입니다⏰ \n10문제 중 7문제 이상 맞추게 되면 성공하게 됩니다! \n단, 주제는 1가지만 선택 가능하고, \n결과는 10문제를 모두 푼 후에 확인 가능합니다.";

  return (
    <div>
      <div className="flex flex-wrap justify-center mx-auto mt-10 mb-14">
        <Image
          src="/images/gameMachine.png"
          alt="gameMachine"
          width={180}
          height={180}
          priority={true}
        />
        <div className="flex flex-col justify-evenly">
          <div className="flex items-center justify-around font-semibold text-5xl tracking-widest">
            <div className="text-f5black-400 m-2">PICK</div>
            <div className="text-f5green-300 m-2">IT</div>
            <div className="text-f5black-400 m-2">GAME</div>
          </div>
          <div className="flex items-center justify-center">
            <div className="font-medium text-f5black-400">
              게임을 통해 재밌게 면접을 준비해보세요!
            </div>
            <Tooltip content={gameInfo}>
              <IoHelpCircleSharp
                size={35}
                className=" text-f5gray-400 hover:text-f5gray-500 hover:cursor-pointer transition-all duration-150 ease-in-out"
              />
            </Tooltip>
          </div>
        </div>
      </div>
      <div className="flex flex-wrap justify-evenly mx-20">
        <div>
          <div className="flex justify-center font-semibold text-3xl tracking-widest mb-10">
            <div className="text-f5green-300 mr-3">SPEED</div>
            <div className="text-f5black-400">QUIZ</div>
          </div>
          <Link href="/game/speedQuiz">
            <Image
              src="/images/speedQuiz.png"
              alt="speedQuiz"
              width={500}
              height={350}
              priority={true}
              className="transition-all ease-in-out hover:-translate-y-1 hover:scale-105 duration-500"
            />
          </Link>
        </div>
        <div>
          <div className="flex justify-center font-semibold text-3xl tracking-widest mb-10">
            <div className="text-f5green-300 mr-3">OX</div>
            <div className="text-f5black-400">QUIZ</div>
          </div>
          <Link href="/game/speedQuiz">
            <Image
              src="/images/OXQuiz.png"
              alt="OXQuiz"
              width={500}
              height={350}
              priority={true}
              className="transition-all ease-in-out hover:-translate-y-1 hover:scale-105  duration-500"
            />
          </Link>
        </div>
      </div>
    </div>
  );
}
