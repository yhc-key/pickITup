import Image from "next/image";

import { IoHelpCircleSharp } from "react-icons/io5";

import Tooltip from "@/components/tooltip";
import TechSelectSpeed from "@/components/game/SpeedQuiz/techSelectSpeed";
import TechSelectOX from "@/components/game/OXQuiz/techSelectOX";

export default function GamePage() {
  const gameInfo: string =
    "한 게임은 총 10문제로 구성되어 있으며 \n한 문제당 제한시간은 10초입니다⏰ \n10문제 중 7문제 이상 맞추게 되면 성공하게 됩니다! \n단, 주제는 1가지만 선택 가능하고, \n결과는 10문제를 모두 푼 후에 확인 가능합니다.";

  return (
    <div>
      <div className="flex flex-wrap justify-center mx-auto mt-4 mb-8">
        <Image
          src="/images/gameMachine.png"
          alt="gameMachine"
          width={150}
          height={150}
          priority={true}
        />
        <div className="flex flex-col justify-evenly">
          <div className="flex justify-around text-5xl font-semibold tracking-wider">
            <div className="my-2 mr-2 text-f5black-400">PICK</div>
            <div className="mx-1 my-2 text-f5green-300">IT</div>
            <div className="my-2 ml-2 text-f5black-400">GAME</div>
          </div>
          <div className="flex items-center justify-center">
            <div className="font-medium text-f5black-400">
              게임을 통해 재밌게 면접을 준비해보세요!
            </div>
            <Tooltip content={gameInfo}>
              <IoHelpCircleSharp
                size={35}
                className="transition-all duration-150 ease-in-out text-f5gray-400 hover:text-f5gray-500 hover:cursor-pointer"
              />
            </Tooltip>
          </div>
        </div>
      </div>
      <div className="flex flex-wrap mx-20 mb-10 justify-evenly">
        <div>
          <div className="flex justify-center my-5 text-3xl font-semibold tracking-widest">
            <div className="mr-3 text-f5green-300">SPEED</div>
            <div className="text-f5black-400">QUIZ</div>
          </div>
          <TechSelectSpeed />
        </div>
        <div>
          <div className="flex justify-center my-5 text-3xl font-semibold tracking-widest">
            <div className="mr-3 text-f5green-300">OX</div>
            <div className="text-f5black-400">QUIZ</div>
          </div>
          <TechSelectOX />
        </div>
      </div>
    </div>
  );
}
